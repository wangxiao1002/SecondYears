
from django.urls import path,include
from .sso import urls as sso_urls
from .user import urls as user_urls
from .public import urls as public_urls
from .wechat import urls as wechat_urls

urlpatterns = [
    path('sso/', include(sso_urls)),
    path('user/', include(user_urls)),
    path('public/', include(public_urls)),
    path('wechat/', include(wechat_urls)),
]
