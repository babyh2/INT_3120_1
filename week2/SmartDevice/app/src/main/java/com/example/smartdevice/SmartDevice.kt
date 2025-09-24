import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SmartDevice(val name: String, val category: String) {

    var deviceStatus = "online"
        protected set

    open val deviceType = "unknown"

    open fun turnOn() {
        deviceStatus = "on"
    }

    open fun turnOff() {
        deviceStatus = "off"
    }

    fun printDeviceInfo() {
        println("Device name: $name, category: $category, type: $deviceType")
    }
}

class SmartTvDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart TV"

    private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)

    private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)

    fun increaseSpeakerVolume() {
        speakerVolume++
        println("Speaker volume increased to $speakerVolume.")
    }

    fun decreaseVolume() {
        speakerVolume--
        println("Speaker volume decreased to $speakerVolume.")
    }

    fun nextChannel() {
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }

    fun previousChannel() {
        channelNumber--
        println("Channel number decreased to $channelNumber.")
    }

    override fun turnOn() {
        super.turnOn()
        println(
            "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                    "set to $channelNumber."
        )
    }

    override fun turnOff() {
        super.turnOff()
        println("$name turned off")
    }
}

class SmartLightDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart Light"

    private var brightnessLevel by RangeRegulator(initialValue = 0, minValue = 0, maxValue = 100)

    fun increaseBrightness() {
        brightnessLevel++
        println("Brightness increased to $brightnessLevel.")
    }

    fun decreaseBrightness() {
        brightnessLevel--
        println("Brightness decreased to $brightnessLevel.")
    }

    override fun turnOn() {
        super.turnOn()
        brightnessLevel = 2
        println("$name turned on. The brightness level is $brightnessLevel.")
    }

    override fun turnOff() {
        super.turnOff()
        brightnessLevel = 0
        println("Smart Light turned off")
    }
}

class SmartHome(
    val smartTvDevice: SmartTvDevice,
    val smartLightDevice: SmartLightDevice
) {

    var deviceTurnOnCount = 0
        private set

    fun turnOnTv() {
        if (smartTvDevice.deviceStatus != "on") {
            smartTvDevice.turnOn()
            deviceTurnOnCount++
        } else {
            println("${smartTvDevice.name} is already on.")
        }
    }

    // Turn off TV: chỉ tắt nếu TV đang "on"
    fun turnOffTv() {
        if (smartTvDevice.deviceStatus == "on") {
            smartTvDevice.turnOff()
            deviceTurnOnCount--
        } else {
            println("${smartTvDevice.name} is not on.")
        }
    }

    fun increaseTvVolume() {
        if (smartTvDevice.deviceStatus == "on") {
            smartTvDevice.increaseSpeakerVolume()
        } else {
            println("Cannot increase volume: ${smartTvDevice.name} is not on.")
        }
    }

    fun decreaseTvVolume() {
        if (smartTvDevice.deviceStatus == "on") {
            smartTvDevice.decreaseVolume()
        } else {
            println("Cannot decrease volume: ${smartTvDevice.name} is not on.")
        }
    }

    fun changeTvChannelToNext() {
        if (smartTvDevice.deviceStatus == "on") {
            smartTvDevice.nextChannel()
        } else {
            println("Cannot change channel: ${smartTvDevice.name} is not on.")
        }
    }

    fun changeTvChannelToPrevious() {
        if (smartTvDevice.deviceStatus == "on") {
            smartTvDevice.previousChannel()
        } else {
            println("Cannot change channel: ${smartTvDevice.name} is not on.")
        }
    }

    fun printSmartTvInfo() {
        smartTvDevice.printDeviceInfo()
    }

    fun turnOnLight() {
        if (smartLightDevice.deviceStatus != "on") {
            smartLightDevice.turnOn()
            deviceTurnOnCount++
        } else {
            println("${smartLightDevice.name} is already on.")
        }
    }

    fun turnOffLight() {
        if (smartLightDevice.deviceStatus == "on") {
            smartLightDevice.turnOff()
            deviceTurnOnCount--
        } else {
            println("${smartLightDevice.name} is not on.")
        }
    }

    fun increaseLightBrightness() {
        if (smartLightDevice.deviceStatus == "on") {
            smartLightDevice.increaseBrightness()
        } else {
            println("Cannot increase brightness: ${smartLightDevice.name} is not on.")
        }
    }

    fun decreaseLightBrightness() {
        if (smartLightDevice.deviceStatus == "on") {
            smartLightDevice.decreaseBrightness()
        } else {
            println("Cannot decrease brightness: ${smartLightDevice.name} is not on.")
        }
    }

    fun printSmartLightInfo() {
        smartLightDevice.printDeviceInfo()
    }
}

class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {

    var fieldData = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue) {
            fieldData = value
        }
    }
}

fun main() {
    val tv = SmartTvDevice("Android TV", "Entertainment")
    val light = SmartLightDevice("Google Light", "Utility")
    val home = SmartHome(tv, light)

    println("=== Test TV actions ===")
    home.increaseTvVolume()          // TV chưa on -> sẽ báo lỗi
    home.turnOnTv()                  // bật TV (count++)
    home.increaseTvVolume()          // tăng âm lượng
    home.decreaseTvVolume()          // giảm âm lượng
    home.changeTvChannelToNext()     // next channel
    home.changeTvChannelToPrevious() // previous channel
    home.printSmartTvInfo()          // in info TV

    println("\n=== Test Light actions ===")
    home.increaseLightBrightness()   // light chưa on -> báo lỗi
    home.turnOnLight()               // bật light (count++)
    home.increaseLightBrightness()   // tăng độ sáng
    home.decreaseLightBrightness()   // giảm độ sáng
    home.printSmartLightInfo()       // in info light

    println("\nDevice turn-on count = ${home.deviceTurnOnCount}")

    println("\n=== Turn off all devices ===")
    home.turnOffAllDevices()         // tắt cả (count giảm)
    println("Device turn-on count = ${home.deviceTurnOnCount}")

    // Thử thao tác khi đã tắt
    home.decreaseTvVolume()          // sẽ báo không thể vì TV không on
    home.decreaseLightBrightness()   // sẽ báo không thể vì light không on
}
