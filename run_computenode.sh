source setenvcsh.sh
ant -q -Dcomputenodeid=0 computenodeserver &
ant -q -Dcomputenodeid=1 computenodeserver &
ant -q -Dcomputenodeid=2 computenodeserver &
ant -q -Dcomputenodeid=3 computenodeserver &
bash

