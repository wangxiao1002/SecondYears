import geoip2.database
from project.settings import STATIC_PATH

def getAddrByIp(ip=None):

    reader = geoip2.database.Reader(STATIC_PATH + '/GeoLite2-City.mmdb')
    country = "未知"

    try:

        response = reader.city(ip)
        if "zh-CN" in response.country.names:
            country = response.country.names["zh-CN"]
        else:
            country = response.country.name

        if "zh-CN" in response.subdivisions.most_specific.names:
            subdivisions = response.subdivisions.most_specific.names["zh-CN"]
        else:
            subdivisions = response.subdivisions.most_specific.name

        if "zh-CN" in response.city.names:
            city = response.city.names["zh-CN"]
        else:
            city = response.city.name

        return "{} {}{}".format(country,subdivisions,city)
    except Exception :
        return "{}".format(country)


if __name__ == '__main__':
    print(getAddrByIp('49.144.204.179'))
