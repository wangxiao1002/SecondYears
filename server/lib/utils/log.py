# coding=utf-8
'''
@summary: 始化logger实例(对logging的封装)
@usage：
          # >>> from .log import logger
          # >>> logger.error(u'系统开小差了！')
'''

# 使用python的logging模块，配合settings的LOGGING属性
import logging
import sys
import traceback

# root--用于平台的日志记录
logger_detail = logging.getLogger('root')
# component--用于组件调用的日志记录
logger_component = logging.getLogger('component')

class logger_traceback:
    """
    详细异常信息追踪
    """
    def __init__(self):
        pass
    
    def error(self, message=''):
        """
        error 日志
        """
        message = self.get_error_info(message)
        logger_detail.error(message)
    
    def info(self, message=''):
        """
        info 日志
        """
        message = self.get_error_info(message)
        logger_detail.info(message)
    
    def warning(self, message=''):
        """
        warning 日志
        """
        message = self.get_error_info(message)
        logger_detail.warning(message)
    
    def debug(self, message=''):
        """
        debug 日志
        """
        message = self.get_error_info(message)
        logger_detail.debug(message)
    
    def critical(self, message=''):
        """
        critical 日志
        """
        message = self.get_error_info(message)
        logger_detail.critical(message)
    
    def get_error_info(self, message):
        """
        获取日志信息
        """
        try:
            info = sys.exc_info()
            for filename, lineno, function, text in traceback.extract_tb(info[2]):
                msg = u"%s line: %s in %s" % (filename, lineno, function)
                message = u"%s;%s\n%s\n" % (message, msg, text)
            sys.exc_clear()
            return message
        except Exception as e:
            return message
        
# traceback--打印详细错误日志    
logger = logger_traceback()

