# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.17

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Disable VCS-based implicit rules.
% : %,v


# Disable VCS-based implicit rules.
% : RCS/%


# Disable VCS-based implicit rules.
% : RCS/%,v


# Disable VCS-based implicit rules.
% : SCCS/s.%


# Disable VCS-based implicit rules.
% : s.%


.SUFFIXES: .hpux_make_needs_suffix_list


# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "C:\Program Files\JetBrains\CLion 2020.3.1\bin\cmake\win\bin\cmake.exe"

# The command to remove a file.
RM = "C:\Program Files\JetBrains\CLion 2020.3.1\bin\cmake\win\bin\cmake.exe" -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "C:\Users\johne\Documents\U of Iowa\Spring2021\ENGR-2730\jelwart\homework\Homework 1"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "C:\Users\johne\Documents\U of Iowa\Spring2021\ENGR-2730\jelwart\homework\Homework 1\cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles/Homework_1.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Homework_1.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Homework_1.dir/flags.make

CMakeFiles/Homework_1.dir/main.cpp.obj: CMakeFiles/Homework_1.dir/flags.make
CMakeFiles/Homework_1.dir/main.cpp.obj: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="C:\Users\johne\Documents\U of Iowa\Spring2021\ENGR-2730\jelwart\homework\Homework 1\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/Homework_1.dir/main.cpp.obj"
	C:\PROGRA~1\mingw-w64\x86_64-7.3.0-posix-seh-rt_v5-rev0\mingw64\bin\g++.exe  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles\Homework_1.dir\main.cpp.obj -c "C:\Users\johne\Documents\U of Iowa\Spring2021\ENGR-2730\jelwart\homework\Homework 1\main.cpp"

CMakeFiles/Homework_1.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Homework_1.dir/main.cpp.i"
	C:\PROGRA~1\mingw-w64\x86_64-7.3.0-posix-seh-rt_v5-rev0\mingw64\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "C:\Users\johne\Documents\U of Iowa\Spring2021\ENGR-2730\jelwart\homework\Homework 1\main.cpp" > CMakeFiles\Homework_1.dir\main.cpp.i

CMakeFiles/Homework_1.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Homework_1.dir/main.cpp.s"
	C:\PROGRA~1\mingw-w64\x86_64-7.3.0-posix-seh-rt_v5-rev0\mingw64\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S "C:\Users\johne\Documents\U of Iowa\Spring2021\ENGR-2730\jelwart\homework\Homework 1\main.cpp" -o CMakeFiles\Homework_1.dir\main.cpp.s

# Object files for target Homework_1
Homework_1_OBJECTS = \
"CMakeFiles/Homework_1.dir/main.cpp.obj"

# External object files for target Homework_1
Homework_1_EXTERNAL_OBJECTS =

Homework_1.exe: CMakeFiles/Homework_1.dir/main.cpp.obj
Homework_1.exe: CMakeFiles/Homework_1.dir/build.make
Homework_1.exe: CMakeFiles/Homework_1.dir/linklibs.rsp
Homework_1.exe: CMakeFiles/Homework_1.dir/objects1.rsp
Homework_1.exe: CMakeFiles/Homework_1.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="C:\Users\johne\Documents\U of Iowa\Spring2021\ENGR-2730\jelwart\homework\Homework 1\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable Homework_1.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\Homework_1.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Homework_1.dir/build: Homework_1.exe

.PHONY : CMakeFiles/Homework_1.dir/build

CMakeFiles/Homework_1.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\Homework_1.dir\cmake_clean.cmake
.PHONY : CMakeFiles/Homework_1.dir/clean

CMakeFiles/Homework_1.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" "C:\Users\johne\Documents\U of Iowa\Spring2021\ENGR-2730\jelwart\homework\Homework 1" "C:\Users\johne\Documents\U of Iowa\Spring2021\ENGR-2730\jelwart\homework\Homework 1" "C:\Users\johne\Documents\U of Iowa\Spring2021\ENGR-2730\jelwart\homework\Homework 1\cmake-build-debug" "C:\Users\johne\Documents\U of Iowa\Spring2021\ENGR-2730\jelwart\homework\Homework 1\cmake-build-debug" "C:\Users\johne\Documents\U of Iowa\Spring2021\ENGR-2730\jelwart\homework\Homework 1\cmake-build-debug\CMakeFiles\Homework_1.dir\DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles/Homework_1.dir/depend

