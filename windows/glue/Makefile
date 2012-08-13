CSC=mcs
CSFLAGS=
CSLIBS= -r:../BuzzIO.dll

#%.exe: %.cs
	#$(CSC) $(CSFLAGS) $(CSLIBS) -o $@ $^

glue.exe: AssemblyInfo.cs IBuzzerLayer.cs BuzzerLayer.cs Server.cs Main.cs 
	$(CSC) $(CSFLAGS) $(CSLIBS) -out:$@ $^


clean:
	-rm *.exe
