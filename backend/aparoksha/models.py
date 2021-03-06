from __future__ import unicode_literals

from django.contrib.auth.models import User
from django.db import models


class Users(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE, related_name='profile')
    user_name = models.CharField(max_length=25)
    user_fname = models.CharField(max_length=40, blank=True, null=True)
    user_lname = models.CharField(max_length=40, blank=True, null=True)
    user_email = models.CharField(primary_key=True, max_length=60)
    joining_date = models.DateTimeField()
    user_gender = models.CharField(max_length=1)
    user_pic = models.CharField(max_length=255, blank=True, null=True)
    user_about = models.CharField(max_length=512, blank=True, null=True)
    user_type = models.IntegerField(null=True)      # 1. User    2. NGO
    user_ngo_type = models.IntegerField(null=True)  # 1. Money   2. Kind

    class Meta:
        verbose_name = 'Users'
        verbose_name_plural = 'Users'

    def __unicode__(self):
        return self.user_name

    def __str__(self):
        return self.user_name

    def as_dict(self):
        return {
            "Username": self.user_name,
            "UserPic": self.user_pic,
            "UserMail": self.user_email,
            "About": self.user_about,
        }

class Events(models.Model):
    Event_id = models.AutoField(primary_key=True)
    Event_pic = models.CharField(max_length=255, blank=True, null=True)
    Event_title = models.CharField(max_length=512, null=True, blank=True)
    Event_desc = models.CharField(max_length=512, null=True, blank=True)
    Event_date = models.DateTimeField()
    Event_location = models.CharField(max_length=512, null=True, blank=True)
    Event_org  = models.ForeignKey(Users,null=True)
    class Meta:
        verbose_name = 'Events'
        verbose_name_plural = 'Events'
    def __unicode__(self):
        return self.Event_title

    def __str__(self):
        return self.Event_title

    def as_dict(self):
        return {
            "title": self.Event_title,
            "org": self.Event_org.user_email,
            "desc": self.Event_desc,
            "date":str(self.Event_date),
            "location": self.Event_location,
        }


class Donations(models.Model):
    donation_id = models.AutoField(primary_key=True)
    donation_type = models.IntegerField()    # 1: Money  2: Food  3: Clothes   4.Books etc
    amount_people = models.IntegerField(null=True)
    donation_desc = models.CharField(max_length=512, null=True, blank=True)
    donation_to = models.CharField(max_length=255, null=True)
    donation_from = models.CharField(max_length=255, null=False)
    donation_email = models.CharField(max_length=255)
    donation_date = models.DateTimeField()
    donation_status = models.CharField(max_length=255, default='Pending')  # Pending/Approved/Completed
    # After Confirmation of orders to donor
    donation_Receiver = models.CharField(max_length=255, null=True)        # Receiver
    donation_mobile = models.CharField(max_length=255, null=True)          # Receiver's Mobile
    donation_time = models.DateTimeField(null=True)                        # At what time we are accepting
    donor_coordinates_lat = models.FloatField(null=True)                   # Latitude  of donor
    donor_coordinates_lon = models.FloatField(null=True)                   # Longitude of donor

    donating_user = models.ForeignKey(Users, null=True)

    class Meta:
        verbose_name = 'Donations'
        verbose_name_plural = 'Donations'

    def __unicode__(self):
        return self.donation_from

    def __str__(self):
        return self.donation_from

    def as_dict(self):
        return {
            "id":self.donation_id,
            "type": str(self.donation_type),
            "from": self.donation_from,
            "org": self.donation_to,
            "date": str(self.donation_date),
            "status": self.donation_status,
            "receiver":self.donation_Receiver,
            "phone":self.donation_mobile,
        }