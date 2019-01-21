March 7, 2002

Modified the globalplatform.opt to reflect the newly assigned GlobalPlatform AID i.e.
0xA0:0x00:0x00:0x01:051:0x00
Used my own environment to generate new .exp and .jca file.
Manually modified globalplatform_exp.tex to reflect new AID.

October 9, 2001

Modified the source code so that the CVM interface is now part of the package 
org.globalpatform.   Removed the package org.globalplatformx
Changed the AID so that it is now 'GPPKGEXP01' correctly in hex. (This is still 
temporary).
Introduced a Makefile with targets 'classes', 'exportfile', and 'docs'.


----------------


August 21, 2001

This zip contains the files used to generate template export files for Open Platform 2.1.
The AID used are of the form 'GPPKGEXP01' and 'GPPKGEXP02'

AID used                                             Export File
0x71:0x80:0x80:0x75:0x71:0x69:0x88:0x80:0x48:0x49    org\globalplatform\javacard\globalplatform.exp
0x71:0x80:0x80:0x75:0x71:0x69:0x88:0x80:0x48:0x4A    org\globalplatformx\javacard\globalplatformx.exp

I have also included the original java files and the config files used for the converter v1.1. (.opt extensions)
converter -config org\globalplatform\globalplatform.opt
converter -config org\globalplatformx\globalplatformx.opt
