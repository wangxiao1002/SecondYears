
import time
import datetime
import random
import calendar

class UtilTime(object):

    def __init__(self, timezone='local', arrow=None):

        self.arrow = arrow
        if not self.arrow:
            import arrow
            self.arrow = arrow
        #时区
        self.timezone = timezone

    # 获取当前时间的arrow结构
    @property
    def today(self):
        return self.arrow.now(self.timezone)

    # 当前时间戳
    @property
    def timestamp(self):
        return self.today.timestamp

    # 获取当前时间,自定义format
    def get_today_format(self, format_v="YYYY-MM-DD HH:mm:ss"):
        return self.today.format(format_v)

    # 时间戳转arrow
    def timestamp_to_arrow(self, timestamp=None):
        return self.arrow.get(timestamp).to(
            self.timezone) if timestamp else timestamp

    # arrow转时间戳
    def arrow_to_timestamp(self, arrow_v=None):
        return arrow_v.timestamp if arrow_v else arrow_v

    #arrow转字符串
    def arrow_to_string(self, arrow_s=None, format_v="YYYY-MM-DD HH:mm:ss"):
        return arrow_s.format(format_v) if arrow_s else self.today.format(format_v)

    #字符串转arrow
    def string_to_arrow(self, string_s=None, format_v="YYYY-MM-DD HH:mm:ss"):
        return self.arrow.get(
            string_s, format_v, tzinfo=self.timezone) if string_s else string_s

    #时间戳转字符串
    def timestamp_to_string(self, timestamp,format_v="%Y-%m-%d %H:%M:%S"):

        time_local = time.localtime(int(timestamp))
        return time.strftime(format_v, time_local)

    #时间戳转arrow
    def timestamp_to_arrow(self, timestamp):
        return self.arrow.get(timestamp)

    #字符串转时间戳
    def string_to_timestamp(self, string_s=None,
                            format_v="YYYY-MM-DD HH:mm:ss"):
        return self.string_to_arrow(
            string_s, format_v).timestamp if string_s else string_s

    #时间日期加减
    def replace(self, arrow_v, **kwargs):
        """
			example :
				days = -1  减一天
				weeks = -1 减一周
				mounts = -1 减一个月
				quarters = -1 减一个季度
				years = -1 减一年
				hours = -1 减一小时
				minutes = -1 减一分钟
				seconds = -1 减一秒钟
		"""
        return arrow_v.shift(**kwargs)

    #判断周几
    def get_week_day(self, todays=None):
        """
			todays: "YYYY-MM-DD" 字符串
			周一:1 ... 周日:7
		"""
        format_v = "YYYY-MM-DD"
        day_arrow = self.today if not todays else self.string_to_arrow(
            todays, format_v)
        day_string = self.arrow_to_string(
            self.today, format_v) if not todays else self.arrow_to_string(
                self.string_to_arrow(todays, format_v), format_v)

        week1 = day_arrow.floor('week')
        if self.arrow_to_string(week1, format_v) == day_string:
            return 1
        elif self.arrow_to_string(week1.shift(days=1),
                                  format_v) == day_string:
            return 2
        elif self.arrow_to_string(week1.shift(days=2),
                                  format_v) == day_string:
            return 3
        elif self.arrow_to_string(week1.shift(days=3),
                                  format_v) == day_string:
            return 4
        elif self.arrow_to_string(week1.shift(days=4),
                                  format_v) == day_string:
            return 5
        elif self.arrow_to_string(week1.shift(days=5),
                                  format_v) == day_string:
            return 6
        elif self.arrow_to_string(week1.shift(days=6),
                                  format_v) == day_string:
            return 7
        else:
            return None


def send_toTimestamp(t):
    t1=str(t)
    t2=time.strptime(t1[0:19],"%Y-%m-%d %H:%M:%S")
    return time.mktime(t2)

def get_current_month_start_and_end(date):
    """
    年份 date(2017-09-08格式)
    :param date:
    :return:本月第一天日期和本月最后一天日期
    """
    if date.count('-') != 2:
        raise ValueError('- is error')
    year, month = str(date).split('-')[0], str(date).split('-')[1]
    end = calendar.monthrange(int(year), int(month))[1]
    start_date = '%s-%s-01' % (year, month)
    end_date = '%s-%s-%s' % (year, month, end)
    return start_date, end_date

if __name__ == '__main__':
    ut = UtilTime()

    # date_arrow = ut.today.shift(days=-1).shift(months=-2)
    # print(date_arrow)

    """
        按天
    """
    #
    # start_date = "2020-05-19"
    # end_date="2020-06-18"
    #
    # ut = UtilTime()
    # s = ut.string_to_arrow(start_date+' 00:00:00')
    # e = ut.string_to_arrow(end_date+' 00:00:00').shift(days=1)
    #
    # while e.timestamp > s.timestamp:
    #     start = ut.string_to_arrow(e.shift(days=-1).format("YYYY-MM-DD"), format_v="YYYY-MM-DD")
    #     end = e
    #
    #     e = e.shift(days=-1)
    #     print(ut.arrow_to_string(start),ut.arrow_to_string(end))


    """
        按小时
    """
    # start_date = "2020-04-19"
    # end_date="2020-05-19"
    #
    # ut = UtilTime()

    # s = ut.string_to_arrow(start_date+' 00:00:00')
    # e = ut.string_to_arrow(end_date+' 00:00:00').shift(days=1)
    #
    # h = (e-s).days*24
    # h_tmp = 1
    # today  = ut.string_to_arrow(ut.arrow_to_string(ut.today)[:13] + ':00:00')
    #
    # if e>today:
    #     e=today
    #
    # while h_tmp<=h:
    #     start = ut.arrow_to_string(e.shift(hours=h_tmp*-1))
    #     end = ut.arrow_to_string(e.shift(hours=(h_tmp-1)*-1))
    #
    #     h_tmp+=1
    #
    #     print(start,end)

    """
        按周
    """


    # start_date = "2020-06-08"
    # end_date="2020-06-18"
    #
    # start_data_arrow = ut.string_to_arrow(start_date, "YYYY-MM-DD")
    # end_date_arrow = ut.string_to_arrow(end_date,"YYYY-MM-DD")
    #
    # if ut.get_week_day(start_date) == 1:
    #     s = start_data_arrow
    # else:
    #     s = start_data_arrow.floor('week').shift(weeks=1)
    #
    # if ut.get_week_day(end_date) == 7:
    #     e = end_date_arrow
    # else:
    #     e = end_date_arrow.floor('week').shift(weeks=-1).shift(weeks=1).shift(days=-1)
    #
    # if e<s:
    #     print("错误!")
    #
    # today  = ut.today.floor('week').shift(days=-1)
    #
    # if e>today:
    #     e=today
    #
    # w = int((e.shift(days=1)-s).days/7)
    # w_tmp = 1
    #
    # while w_tmp<=w:
    #     start = ut.arrow_to_string(e.shift(weeks=w_tmp*-1).shift(days=1))
    #     end = ut.arrow_to_string(e.shift(weeks=(w_tmp-1)*-1).shift(days=1))
    #
    #     w_tmp+=1
    #
    #     print(start,end)


    """
        按月
    """

    # ut = UtilTime()
    # m = 1
    # # today  = ut.string_to_arrow( + '-00 00:00:00')
    #
    # today = ut.string_to_arrow(ut.today.format("YYYY-MM"),format_v="YYYY-MM")
    #
    # while m<=3:
    #     start = ut.arrow_to_string(today.shift(months=m*-1))
    #     end = ut.arrow_to_string(today.shift(months=(m-1)*-1))
    #
    #     m+=1
    #
    #     print(start,end)

    date="2020-06-17"

    date_arrow = ut.string_to_arrow(date, "YYYY-MM-DD")

    today_arrow = ut.string_to_arrow(ut.today.format("YYYY-MM-DD"), "YYYY-MM-DD")
    c = 0

    while c < 7:
        tmp_start_date_arrow = date_arrow.shift(days=c*-1)
        tmp_end_date_arrow = tmp_start_date_arrow.shift(days=1)

        d = (today_arrow - tmp_start_date_arrow).days

        print(d,tmp_start_date_arrow,tmp_end_date_arrow)
        d_tmp =1
        while d_tmp <= d:
            tmp_start_date_arrow = tmp_start_date_arrow.shift(days=1)
            tmp_end_date_arrow = tmp_start_date_arrow.shift(days=1)
            print(tmp_start_date_arrow,tmp_end_date_arrow)
            d_tmp += 1

        c +=1