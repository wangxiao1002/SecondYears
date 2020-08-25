



from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .api import *

router = DefaultRouter(trailing_slash=False)
router.register('', WeChatAPIView, base_name='wechat')

urlpatterns = [
    path('', include(router.urls))
]