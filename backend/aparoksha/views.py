#!/usr/bin/python
# -*- coding: utf-8 -*-
from django.contrib.auth.decorators import login_required
from django.db.models import Q
from django.http import HttpResponse
from django.shortcuts import render
from django.utils import timezone
from django.utils.html import strip_tags
from django.views.decorators.csrf import csrf_exempt
from datetime import datetime
from models import Users, Donations, Events
from django.contrib.auth.models import User
from django.contrib.auth import authenticate, login as django_login, \
    logout
import json


@csrf_exempt
def login(request):
    if request.method == 'GET':
        user_email = request.GET.get('user_email')
        print user_email
        user = User.objects.get(email=user_email)
        print user
        profile = Users.objects.get(user_email=user_email)
        donated = profile.donations_set.all()
        donations = []
        for i in range(len(donated)):
            donations.append(donated[i].as_dict())
        user1 = authenticate(username=user.username,
                             password=request.GET.get('password'))
        print user1
        if user1 is not None:
            django_login(request, user1)
            profile_details = {
                'success': 'True',
                'username': profile.user_name,
                'user_email': profile.user_email,
                'previous_donation': donations,
                'user_gender': profile.user_gender,
                'user_type': profile.user_type,
                }
            return HttpResponse(json.dumps(profile_details),
                                content_type='application/json')
    else:
        return HttpResponse(json.dumps({'success': 'False'}),
                            content_type='application/json')


@csrf_exempt
def signup(request):
    if request.method == 'GET':
        username = request.GET.get('user_name')
        print username
        print request.GET.get('user_email')
        email_exists = clean_user_email(request.GET.get('user_email'))
        print email_exists

        if email_exists is not None:

            print email_exists
            user_fname = request.GET.get('user_fname')
            user_lname = request.GET.get('user_lname')
            user_gender = request.GET.get('user_gender')
            user_password = request.GET.get('user_password')
            user_type = request.GET.get('user_type')
            new_user = User.objects.create_user(username=username,
                    password=user_password, email=email_exists)
            new_user.save()
            try:
                new_profile = Users.objects.create(
                    user=new_user,
                    user_name=username,
                    user_email=email_exists,
                    user_gender=user_gender,
                    user_fname=user_fname,
                    user_lname=user_lname,
                    user_type=user_type,
                    joining_date=timezone.now(),
                    )
                print 'hello world'
                new_profile.save()
                return HttpResponse(json.dumps({'success': 'True'}),
                                    content_type='application/json')
            except Exception, e:

                return HttpResponse('Exception : ' + str(e))
                new_profile.save()
        else:
            return HttpResponse(json.dumps({'success': 'False'}),
                                content_type='application/json')


def clean_user_email(user_email):
    try:
        user = User.objects.get(email=user_email)
        print 'hello'
    except:
        return user_email


def Notifications(request):
    if request.method == 'GET':
        user_email = request.GET.get('user_email')
        print user_email
        profile = Users.objects.get(user_email=user_email)
        print profile.user_type
        donors_pending = []
        ngo_list = []
        event_list = []
        if profile.user_type == 1:  # User
            donations = \
                Donations.objects.all().filter(donation_email=user_email)
        else:

               # NGO

            donations = \
                Donations.objects.all().filter(donation_status='Pending'
                    ).filter(Q(donation_to=None)
                             | Q(donation_to=profile.user_name))
        for i in range(len(donations)):
            donors_pending.append(donations[i].as_dict())
        ngos = Users.objects.all().filter(user_type=2)
        events = Events.objects.all()
        print events
        print donations
        for i in range(len(ngos)):
            ngo_list.append(ngos[i].as_dict())
        for i in range(len(events)):
            event_list.append(events[i].as_dict())
        details = {'user_email':user_email, 'ngo_list': ngo_list,
                   'donation_log': donors_pending,
                   'event_list': event_list}
        return HttpResponse(json.dumps(details),
                            content_type='application/json')


def donate(request):
    if request.method == 'GET':
        x = request.GET.get('donation_type')
        if x == 'Money':
            donation_type = 1
        elif x == 'Food':
            donation_type = 2
        elif x == 'Books':
            donation_type = 3
        else:
            donation_type = 4
        print donation_type
        amount_people = request.GET.get('donation_amount')
        print
        donation_date = timezone.now()
        donation_email = request.GET.get('user_email')
        donation_from = Users.objects.get(user_email=donation_email)
        donation_desc = request.GET.get('donation_description')
        if request.GET.get('donation_to') != 'NotifyAll':
            donation_to = request.GET.get('donation_to')
        else:
            donation_to = None
        lat = request.GET.get('lat')
        lon = request.GET.get('lon')
        new_donate = Donations(
            donation_type=donation_type,
            amount_people=amount_people,
            donation_desc=donation_desc,
            donation_date=donation_date,
            donation_from=donation_from.user_name,
            donation_to=donation_to,
            donation_email=donation_email,
            donating_user=donation_from,
            donor_coordinates_lon=lon,
            donor_coordinates_lat=lat,
            )
        new_donate.save()
        return HttpResponse(json.dumps({'success': 'True'}),
                            content_type='application/json')
    return HttpResponse(json.dumps({'success': 'False'}),
                        content_type='application/json')


def events(request):
    if request.method == 'GET':
        print "hello"
        Event_title = request.GET.get('Event_title')
        print Event_title
        Event_desc = request.GET.get('Event_desc')
        # Event_date = 
        Event_date = datetime.strptime(request.GET.get('Event_date'), '%b %d %Y %I:%M%p')
        print Event_date
        Event_location = request.GET.get('Event_location')
        Event_org = \
            Users.objects.get(user_email=request.GET.get('user_email'))
        new_event = Events(Event_title=Event_title,
                           Event_location=Event_location,
                           Event_date=Event_date,
                           Event_desc=Event_desc, Event_org=Event_org)
        new_event.save()
        return HttpResponse(json.dumps({'success': 'True'}),
                            content_type='application/json')


def donate_accept(request):
    if request.method == 'GET':
        print 'bdcsjdcb'
        donation_receiver = request.GET.get('donation_reciever')
        donation_time = datetime.strptime(request.GET.get('donation_time'), '%b %d %Y %I:%M%p')
        donation_mobile = request.GET.get('donation_mobile')
        # donation_to = request.GET.get('user_email')
        donation = \
            Donations.objects.get(donation_id=request.GET.get('donation_id'
                                  ))
        donation.donation_Receiver = donation_receiver
        donation.donation_time = donation_time
        donation.donation_mobile = donation_mobile
        # donation_donation_to = donation_to
        if donation.donation_status == 'Pending':
            donation.donation_status = 'Approved'
            donation.save()
            return HttpResponse(json.dumps({'success': 'True'}),
                                content_type='application/json')
        else:
            return HttpResponse(json.dumps({'success': 'False'}),
                                content_type='application/json')


def renderLogin(request):
    return render(request, 'Login.html')


def renderSignup(request):
    return render(request, 'SignUp.html')


def renderNotification(request):
    return render(request, 'notification.html')


def renderdonate(request):
    return render(request, 'donation.html')


def renderEvent(request):
    return render(request, 'events.html')


def renderAccept(request):
    return render(request, 'donation_accept.html')


@login_required
def logout_user(request):
    logout(request)
    return HttpResponse(json.dumps({'success': 'True'}),
                        content_type='application/json')

