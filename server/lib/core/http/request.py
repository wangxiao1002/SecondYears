
def get_header(request, key, default=None):
    django_key = 'HTTP_%s' % key.upper().replace('-', '_')
    return request.META.get(django_key, default)

def get_client_ip(request):
    try:
        real_ip = request.META['HTTP_X_FORWARDED_FOR']
        real_ip = real_ip.split(',')[0]
    except:
        try:
            real_ip = request.META['REMOTE_ADDR']
        except:
            real_ip = ''
    return real_ip

def is_valid_ip(request):
    # ip = get_client_ip(request)
    # if ip in ['']:
    #     return True
    # return False

    return True
