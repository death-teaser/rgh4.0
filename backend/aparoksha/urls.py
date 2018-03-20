from django.conf.urls import url
from . import views

urlpatterns = [
    url(r'^renderLogin', views.renderLogin),
    url(r'^renderNotification', views.renderNotification),
    url(r'^renderdonate', views.renderdonate),
    url(r'^renderAccept', views.renderAccept),
    url(r'^login', views.login),
    url(r'^donate_accept', views.donate_accept),
    url(r'^donate', views.donate),
    url(r'^signup$', views.signup),
    url(r'^rendersignup/$', views.renderSignup),
    url(r'^renderEvent/$', views.renderEvent),
    url(r'^Notifications', views.Notifications),
    url(r'^events',views.events),
    url(r'^logout$', views.logout_user),
]
