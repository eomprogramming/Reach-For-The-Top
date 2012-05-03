base_dir = .
nuget = $(base_dir)/packages/Nuget.CommandLine.1.5.21005.9019/tools/Nuget.exe
msbuild = xbuild
config = Release
platform = AnyCPU
bin_dir = $(basedir)/bin
build_dir = $(bin_dir)/$(config)/
release_dir = $(base_dir)/release/
project = $(base_dir)/BuzzIO.csproj
dll_file = $(build_dir)/BuzzIO.dll
nuspec_file = $(base_dir)/BuzzIO.nuspec

build: init
	$(msbuild) $(project) /p:OutDir=$(build_dir) /p:Configuration=$(config) /p:Platform=$(platform)

clean:
	-rm -rf $(bin_dir)
	-rm -rf $(build_dir)
	-rm -rf $(release_dir)
	-rm -rf $(base_dir)/obj

init: clean
	mkdir $(bin_dir)
	mkdir $(build_dir)
	mkdir $(release_dir)
