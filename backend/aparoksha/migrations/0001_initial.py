# -*- coding: utf-8 -*-
# Generated by Django 1.11.4 on 2017-10-28 17:52
from __future__ import unicode_literals

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Donations',
            fields=[
                ('donation_id', models.AutoField(primary_key=True, serialize=False)),
                ('donation_type', models.IntegerField()),
                ('amount_people', models.IntegerField(null=True)),
                ('donation_desc', models.CharField(blank=True, max_length=512, null=True)),
                ('donation_to', models.CharField(max_length=255, null=True)),
                ('donation_from', models.CharField(max_length=255)),
                ('donation_email', models.CharField(max_length=255)),
                ('donation_date', models.DateTimeField()),
                ('donation_status', models.CharField(default='Pending', max_length=255)),
                ('donation_Receiver', models.CharField(max_length=255, null=True)),
                ('donation_mobile', models.CharField(max_length=255, null=True)),
                ('donation_time', models.DateTimeField(null=True)),
                ('donor_coordinates_lat', models.FloatField(null=True)),
                ('donor_coordinates_lon', models.FloatField(null=True)),
            ],
            options={
                'verbose_name': 'Donations',
                'verbose_name_plural': 'Donations',
            },
        ),
        migrations.CreateModel(
            name='Users',
            fields=[
                ('user_name', models.CharField(max_length=25)),
                ('user_fname', models.CharField(blank=True, max_length=40, null=True)),
                ('user_lname', models.CharField(blank=True, max_length=40, null=True)),
                ('user_email', models.CharField(max_length=60, primary_key=True, serialize=False)),
                ('joining_date', models.DateTimeField()),
                ('user_gender', models.CharField(max_length=1)),
                ('user_pic', models.CharField(blank=True, max_length=255, null=True)),
                ('user_about', models.CharField(blank=True, max_length=512, null=True)),
                ('user_type', models.IntegerField(null=True)),
                ('user_ngo_type', models.IntegerField(null=True)),
                ('user', models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, related_name='profile', to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'verbose_name': 'Users',
                'verbose_name_plural': 'Users',
            },
        ),
        migrations.AddField(
            model_name='donations',
            name='donating_user',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='aparoksha.Users'),
        ),
    ]
