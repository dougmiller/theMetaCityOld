#!/usr/bin/python

from bs4 import BeautifulSoup
from urllib.error import HTTPError
import urllib.request

baseURL = 'http://localcity:8080'

checkedURLs = []
excludedURLs = ['/github/', '/twitter/']

errorURLs = []

def checkURL(URLToCheck):

    if URLToCheck[0:4] == "http":
        return   

    if URLToCheck[0:6] == "mailto":
        return    

    if URLToCheck in excludedURLs:
        return

    if URLToCheck not in checkedURLs:
        checkedURLs.append(URLToCheck)
        try:
            fullURL = baseURL + URLToCheck
            f = urllib.request.urlopen(fullURL)
            soup = BeautifulSoup(f.read().decode('utf-8'))

            for link in soup.find_all('a'):
                url = link.get('href')
                checkURL(url)

        except urllib.error.HTTPError:
            errorURLs.append(URLToCheck)


checkURL('/')

print('Checked the following URLs:')
for fineURL in checkedURLs:
    print(fineURL)

print('The following URLs returned an error:')
for errorURL in errorURLs:
    print(errorURL)
