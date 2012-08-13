
all: glue

glue: buzzio
	(cd glue && $(MAKE))

buzzio:
	cp BuzzIO.Makefile BuzzIO/Makefile
	(cd BuzzIO && $(MAKE))
	mv BuzzIO/obj/Release/*.dll .
	-rm BuzzIO/Makefile

clean:
	(cd glue && $(MAKE) clean)
	cp BuzzIO.Makefile BuzzIO/Makefile
	(cd BuzzIO && $(MAKE) clean)
	-rm BuzzIO/Makefile
