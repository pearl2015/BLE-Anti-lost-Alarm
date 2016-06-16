#
#  Copyright (C) 2015 by Sunggyun Jang
#  <mrjang28@gmail.com>
#
# See file CREDITS for list of people who contributed to this
# project.
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License as
# published by the Free Software Foundation; either version 2 of
# the License, or (at your option) any later version.
# 
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place, Suite 330, Boston,
# MA 02111-1307 USA
# 	 

####################################################
####     Tools Path Definition       ####
####################################################
PROJ_NAME = s110_beacon
PART      = ble
TOPDIR 		= C:\GNUARM\workspace\$(PART)\$(PROJ_NAME)
BSPDIR   	= $(TOPDIR)\sources
TARGET		= arm-none-eabi
TOOLDIR 	= C:\GNUARM
LIBCDIR 	= $(TOOLDIR)\$(TARGET)\lib
LIBGCCDIR 	= $(TOOLDIR)\lib\gcc\$(TARGET)\4.7.3

AS			= $(TOOLDIR)\bin\$(TARGET)-as
CC			= $(TOOLDIR)\bin\$(TARGET)-gcc
AR      = $(TOOLDIR)\bin\$(TARGET)-ar -r
NM 			= $(TOOLDIR)\bin\$(TARGET)-nm
LD 			= $(TOOLDIR)\bin\$(TARGET)-ld
SIZE		= $(TOOLDIR)\bin\$(TARGET)-size
OBJCOPY	 	= $(TOOLDIR)\bin\$(TARGET)-objcopy
OBJDUMP 	= $(TOOLDIR)\bin\$(TARGET)-objdump	  

CPU = cortex-m0
MK  = mkdir
RM 	= rm -rf
OBJECT_DIRECTORY = obj
LISTING_DIRECTORY = obj

####################################################
#####      File Definition           ####
####################################################
TARGET_CHIP = NRF51822_QFAA_CA
BOARD = BOARD_PCA10001

# application source
C_FILES += main.c
C_FILES += syscall.c
C_FILES += ble_srv_common.c
C_FILES += ble_advdata.c
C_FILES += softdevice_handler.c
C_FILES += ble_debug_assert_handler.c
C_FILES += system_$(DEVICESERIES).c

ASSEMBLER_FILES += gcc_startup_$(DEVICESERIES).s

SDK_PATH = ./
FILENAME = ble_app_beacon
DEVICE_VARIANT = xxaa
#DEVICE_VARIANT = xxab
USE_SOFTDEVICE = S110
#USE_SOFTDEVICE = S210
DEVICE = NRF51
DEVICESERIES = nrf51
SDK_INCLUDE_PATH = $(SDK_PATH)Include/
SDK_SOURCE_PATH = $(SDK_PATH)SDK/
TEMPLATE_PATH += $(SDK_SOURCE_PATH)templates/gcc/
OUTPUT_BINARY_DIRECTORY = obj
LINKER_SCRIPT = gcc_$(DEVICESERIES)_s110_$(DEVICE_VARIANT).ld
OUTPUT_FILENAME = $(FILENAME)_s110_$(DEVICE_VARIANT)

#		ifeq ($(USE_SOFTDEVICE), S210)
#			LINKER_SCRIPT = gcc_$(DEVICESERIES)_s210_$(DEVICE_VARIANT).ld
#			OUTPUT_FILENAME = $(OUTPUT_FILENAME)_s210_$(DEVICE_VARIANT)

####################################################################
# PATHs                                                            #
####################################################################
INCLUDEPATHS += -I"./"
INCLUDEPATHS += -I"./include/s110"
INCLUDEPATHS += -I"./include/ble"
INCLUDEPATHS += -I"./include/ble/device_manager"
INCLUDEPATHS += -I"./include/ble/ble_services"
INCLUDEPATHS += -I"./include/app_common"
INCLUDEPATHS += -I"./include/sd_common"

C_SOURCE_PATHS += ./sources
C_SOURCE_PATHS += ./sdk/ble
C_SOURCE_PATHS += ./sdk/ble/ble_services
C_SOURCE_PATHS += ./sdk/ble/device_manager
C_SOURCE_PATHS += ./sdk/app_common
C_SOURCE_PATHS += ./sdk/sd_common
C_SOURCE_PATHS += ./sdk/simple_uart

INCLUDEPATHS += -I"./"
INCLUDEPATHS += -I"$(SDK_PATH)include"
INCLUDEPATHS += -I"$(SDK_PATH)include/gcc"
INCLUDEPATHS += -I"$(SDK_PATH)include/ext_sensors"


####################################################################
# Rules                                                            #
####################################################################

# Sorting removes duplicates
BUILD_DIRECTORIES = $(sort $(OBJECT_DIRECTORY) $(OUTPUT_BINARY_DIRECTORY) $(LISTING_DIRECTORY) )

C_SOURCE_FILENAMES = $(notdir $(C_FILES) )
ASSEMBLER_SOURCE_FILENAMES = $(notdir $(ASSEMBLER_FILES) )

# Make a list of source paths
C_SOURCE_PATHS += ./ $(SDK_SOURCE_PATH) $(TEMPLATE_PATH) $(wildcard $(SDK_SOURCE_PATH)*/)  $(wildcard $(SDK_SOURCE_PATH)ext_sensors/*/) $(wildcard $(SDK_SOURCE_PATH)ble/*/)
ASSEMBLER_SOURCE_PATHS = ./ $(SDK_SOURCE_PATH) $(TEMPLATE_PATH) $(wildcard $(SDK_SOURCE_PATH)*/)

C_OBJECTS = $(addprefix $(OBJECT_DIRECTORY)/, $(C_SOURCE_FILENAMES:.c=.o) )
ASSEMBLER_OBJECTS = $(addprefix $(OBJECT_DIRECTORY)/, $(ASSEMBLER_SOURCE_FILENAMES:.s=.o) )

# Set source lookup paths
vpath %.c $(C_SOURCE_PATHS)
vpath %.s $(ASSEMBLER_SOURCE_PATHS)

# Include automatically previously generated dependencies
-include $(addprefix $(OBJECT_DIRECTORY)/, $(COBJS:.o=.d))

####################################################
####             Option Definition              ####
####################################################
CFLAGS = -DDEBUG_NRF_USER -DBLE_STACK_SUPPORT_REQD -DS110

# we do not use heap in this app
ASMFLAGS = -D__HEAP_SIZE=0

# keep every function in separate section. This will allow linker to dump unused functions
CFLAGS += -ffunction-sections

# let linker to dump unused sections
#LDFLAGS = -Wl,--gc-sections

# Linker flags
#LDFLAGS += -L"$(TOOLDIR)/arm-none-eabi/lib/armv6-m"
LDFLAGS += -L"$(TOOLDIR)/lib/gcc/arm-none-eabi/4.7.3/armv6-m"
LDFLAGS += -Xlinker -Map=$(LISTING_DIRECTORY)/$(OUTPUT_FILENAME).map
LDFLAGS += -mcpu=$(CPU) -mthumb -mabi=aapcs -L"$(TEMPLATE_PATH)" -T$(LINKER_SCRIPT) 

# Compiler flags
CFLAGS += -mcpu=$(CPU) -mthumb -mabi=aapcs -D$(DEVICE) -D$(BOARD) -D$(TARGET_CHIP) --std=gnu99


# Assembler flags
ASMFLAGS += -x assembler-with-cpp
 
## Default build target
.PHONY: all
all: clean debug

### Targets
debug:    CFLAGS += -DDEBUG -gdwarf-2 -O0
debug:    ASMFLAGS += -DDEBUG -gdwarf-2 -O0
debug:    $(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).bin $(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).hex

.PHONY: release
release: clean
release:  CFLAGS += -DNDEBUG -O3
release:  ASMFLAGS += -DNDEBUG -O3
release:  $(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).bin $(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).hex

echostuff:
	@echo C_OBJECTS: [$(C_OBJECTS)]
	@echo C_SOURCE_FILES: [$(C_SOURCE_FILES)]
	@echo C_SOURCE_PATHS: [$(C_SOURCE_PATHS)]

## Create build directories
$(BUILD_DIRECTORIES):
	$(MK) $@

## Create objects from C source files
$(OBJECT_DIRECTORY)/%.o: %.c
# Build header dependencies
	$(CC) $(CFLAGS) $(INCLUDEPATHS) -M $< -MF "$(@:.o=.d)" -MT $@
# Do the actual compilation
	$(CC) $(CFLAGS) $(INCLUDEPATHS) -c -o $@ $<

## Assemble .s files
$(OBJECT_DIRECTORY)/%.o: %.s
	$(CC) $(ASMFLAGS) $(INCLUDEPATHS) -c -o $@ $<

## Link C and assembler objects to an .elf file
$(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).elf: $(BUILD_DIRECTORIES) $(C_OBJECTS) $(ASSEMBLER_OBJECTS) $(LIBRARIES)
	$(CC) $(LDFLAGS) $(C_OBJECTS) $(ASSEMBLER_OBJECTS) $(LIBRARIES) -o $(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).elf

## Create binary .bin file from the .elf file
$(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).bin: $(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).elf
	$(OBJCOPY) -O binary $(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).elf $(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).bin	

## Create binary .hex file from the .elf file
$(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).hex: $(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).elf
	$(OBJCOPY) -O ihex $(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).elf $(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).hex
	@echo ---------------------------------------------------------------
	$(SIZE) $(OUTPUT_BINARY_DIRECTORY)/$(OUTPUT_FILENAME).elf
	@echo ---------------------------------------------------------------
	@echo  Finished ...

clean:
	$(RM) $(OUTPUT_BINARY_DIRECTORY)


