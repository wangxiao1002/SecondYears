import paramiko
import time
###################################################
###################################################
###################################################
###################################################
port=22
username="root"




hostname="******"
password='*********'

file_name="*****.jar"
###################################################
###################################################
###################################################
###################################################

transport = paramiko.Transport((hostname,port))
transport.connect(username=username,password=password)

sftp = paramiko.SFTPClient.from_transport(transport)
# 将jar 上传至服务器 /tmp/jar
sftp.put('./target/'+file_name, '/root/'+file_name)
transport.close()


# 创建SSH对象
ssh = paramiko.SSHClient()
# 允许连接不在know_hosts文件中的主机
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
# 连接服务器
ssh.connect(hostname=hostname, port=port, username=username, password=password,allow_agent=False,look_for_keys=False)
# 执行命令
cmd="ps -ef|grep 'java -jar "+file_name+"'|awk '{print $2}'"
print(cmd)
stdin, stdout, stderr = ssh.exec_command(cmd)
# 获取命令结果
result = stdout.read()
pids=result.decode('utf-8').split("\n")
for pid in pids:
	# kill进程
	if len(pid)>0:
		cmd="kill -9 "+pid
		print(cmd)
		stdin, stdout, stderr=ssh.exec_command(cmd)
# 重启进程
cmd="nohup java -jar "+file_name+" --spring.profiles.active=dev >/dev/null 2>&1 & \n"
print(cmd)
chan = ssh.invoke_shell()
chan.send(cmd)
time.sleep(1)
# 关闭连接
ssh.close()

print("SUCCESS")
