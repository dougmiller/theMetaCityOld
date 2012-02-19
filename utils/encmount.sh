#!/bin/bash

if [ -z "$2" ]; then
  echo "Usage:"
  echo "encmount.sh mount remotepoint     # mounts the remote file system"
  echo "encmount.sh unmount remotepoint   # unmounts the remote file system"

fi

commands=(mount unmount)

if [ "$1" == "${commands[0]}" ];  then      # Mounts the file system
  if [ "$2" == "tmc" ];  then
    sshfs doug@enc:/home/www/tmctomcat -p 2225 /media/tmc
  fi

elif [ "$1" == "${commands[1]}" ];  then    # Unmounts the file system
  if [ "$2" == "tmc" ];  then
    sudo umount /media/tmc 
  fi

else
  echo "Don't know what that is."
fi
