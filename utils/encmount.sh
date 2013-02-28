#!/bin/bash

if [[ $# -ne 2 || ( $1 != "mount" && $1 != "unmount" ) ]]; then
  echo "Usage:"
  echo "encmount.sh mount remotepoint       # mount the remote file system"
  echo "encmount.sh unmount remotepoint   # unmount the remote file system"
  exit -1
fi

declare -A mountInfo

# Add mounts as needed
case $2 in
  "tmc")
    mountInfo=( [name]="the MetaCity"
                          [ref]="tmc"
                          [mountCommand]="sshfs doug@enc:/srv/http/tmc /media/tmc"
                          [unmountCommand]="sudo umount /media/tmc");;
  *) 
    echo "Unknown mount. Please provide a known mount."
    exit 2;;
esac

# Proper command found, time to execute it
echo "I am going to: "$1 ${mountInfo[name]}

if [ $1 == "mount" ]; then
  ${mountInfo[mountCommand]}
  if [[ $? -ne 0 ]]; then
    echo "Something went wrong with mounting. See errors above."
  else
    echo "Mounted successfully."
  fi
elif  [ $1 == "unmount" ]; then
  ${mountInfo[unmountCommand]}
  if [[ $? -ne 0 ]]; then
    echo "Something went wrong with unmounting. See errors above."
  else
    echo "Unmounted successfully."
  fi
fi
