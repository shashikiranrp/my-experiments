# common make file rules 

# normal shell operations
MKDIR = mkdir -p
RM = rm 
RM_F = rm -f
RM_R = rm -r
RM_RF = rm -rf
CP = cp
CP_R = cp -r 

# tools settings, from the environment (C & C++)
CC  = $(shell which gcc)
CXX  = $(shell which g++)
CC_C = $(CC) -c
CXX_C = $(CXX) -c
CFLAGS += -Wall
CXXFLAGS += -Wall

ifdef DEBUG_ON
# enable debug flag with zero optimaization level (C & C++)
CFLAGS += -g -O0
CXXFLAGS += -g -O0
endif

# end of common definations

