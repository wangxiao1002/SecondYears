

from django.urls import path, include
from rest_framework.routers import DefaultRouter
from file.api import *

router = DefaultRouter(trailing_slash=False)
router.register('', FileAPIView, base_name='file')


urlpatterns = [
    path('', include(router.urls)),
]