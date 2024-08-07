#!/bin/bash 
chmod -R 777 lab0 
rm -r lab0 
mkdir lab0
cd lab0
mkdir garbodor4
touch pawniard1
mkdir simisear3
touch sunkern0
mkdir surskit5
touch vanilluxe2
cd garbodor4
mkdir raticate
mkdir zebstrika
mkdir ludicolo
touch sudowoodo
cd .. 
cd simisear3 
mkdir metapod
mkdir pawniard
mkdir misdreavus
touch vulpix 
touch sawsbuck
touch cascoon 
cd .. 
cd surskit5 
mkdir politoed 
touch staryu 
mkdir pineco 
touch vullaby 
touch krookodile 
cd .. 
cat > garbodor4/sudowoodo << EOF
Способности  Flail Low Kick Rock Throw Mimic Slam Faint
Attack Rock Tomb Block Rock Slide Counter Sucker Punch Double-Edge
Stone Edge Hammer Arm
EOF
cat > pawniard1 << EOF
Развитые способности
Pressure
EOF
cat > simisear3/vulpix << EOF
Тип покемона  FIRE NONE 
EOF
cat > simisear3/sawsbuck << EOF
Тип диеты 
Herbivore 
EOF
cat > simisear3/cascoon << EOF
Живет  Forest Rainforest
EOF
cat > sunkern0 << EOF
Тип покемона 
GRASS NONE 
EOF
cat > surskit5/staryu << EOF
Возможности  Overland=1 Surface=5 Underwater=5
Sky=5 Jump=2 Power=2 Intelligence=4 Fountain=0
Gilled=0
EOF
cat > surskit5/vullaby << EOF
Возможности  Overland=3 Surface=1 Sky=6 Jump=3
Power1=0 Intelligence=4 Guster=0
EOF
cat > surskit5/krookodile << EOF
Возможности  Overland=8
Surface=6 Burrow=8 Jump=3 Power=4 Intelligence=4 Stealth=0
Groundshaper=0 
EOF
cat > vanilluxe2 << EOF
Ходы  Icy Wind Iron Defense Magic Coat 
Magnet Rise Signal Beam Sleep Talk Snore Uproar Weather Ball‡
EOF

echo "number 1 done"
