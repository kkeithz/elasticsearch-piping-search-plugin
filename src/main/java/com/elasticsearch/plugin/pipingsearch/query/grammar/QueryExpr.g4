grammar QueryExpr;
expr    : actions (' '* '|' ' '* actions)* ;
actions  : STRING (' '+ params)? ;
params  : param (' '+ param)* ;
param   : '[' expr ']'
        | kv
        | STRING
        ;
kv      : STRING ' '* '=' ' '* STRING ;

STRING    : (DQUOTA|SQUOTA|NOQUOTA) ;
NOQUOTA : [a-zA-Z0-9~!@#$%*_\-.,()+']+ ;
DQUOTA  : '"' .*? '"' ;
SQUOTA  : '\'' .*? '\'' ;
