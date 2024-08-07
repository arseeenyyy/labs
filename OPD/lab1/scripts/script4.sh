#!/bin/bash
shopt -s globstar
cd lab0
cat */*e | wc -l >> /tmp/s413052
echo "====================punkt 4.1 done===================="
ls -RS 2>>/tmp/s413052mis | head -4
echo "====================punkt 4.2 done===================="
cat */*d | sort | cat -n
echo "====================punkt 4.3 done===================="
cat garbodor4/sudowoodo simisear3/vulpix simisear3/cascoon | sort
echo "====================punkt 4.4 done===================="
ls -c **/p* 2>/tmp/s413052mis | head -2
echo "====================punkt 4.5 done===================="
cat **/v* | sort -r
echo "====================punkt 4.6 done===================="
cd ..
