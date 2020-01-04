/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// --------- Imports --------- \\

package frc.robot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

/*
    This program allows user interface and sends a value to the arduino via I2C. 
    A method is called somewhere else in the program and sends a value in the 
    LEDStatus enum out to the arduino. The I2C is wired to the DA and CL on the 
    micro, giving it the address of 2. After the enum method is chosen, that 
    specific integer is written out to the arduino, and processed from there.
*/

public class LEDHandler {

    // -- Variable Declarations --\\
    //Init the aruino I2C address
    private int Arduino_Address = 0; 
    //---- Object Declarations ----\\
    //Create I2C object
    private static I2C Responce; 
    //enum for LED status
    //  -- manages the value sent to the arduino
    private enum LEDStatus{
        //Clear leds
        PATTERN_OFF(0),
        //Toggle LED patterns
        PATTERN_SEND(1), 
        //Neutral status
        ALT_BLUE_YELLOW(3), 
        //Full color wipe blue
        BLUE(4),
        //Full color wipe green
        GREEN(5),
        //Full color wipe yellow
        YELLOW(6),  
        //Full color wipe red
        RED(7),
        //Endgame
        SCROLLING_RAINBOW(8), 
        //Moving forward
        FWD_SCROLL_BLUE_YELLOW(9), 
        //Moving backward
        RVS_SCROLL_BLUE_YELLOW(10); 

        //this holds arduino state
        private int LEDStatus_Value;
        private LEDStatus(int value){
         this.LEDStatus_Value = value;
        }
        public int getLEDStatus(){
          return this.LEDStatus_Value;
        }
    }

    //Handler constructor
    // -- pass in the address of the adruino where the data is sent
    public LEDHandler(int Arduino_Address_Value){ 
        //Allow arduino address to change
        Arduino_Address = Arduino_Address_Value; 
        //Declare arduino address
        Responce = new I2C(Port.kOnboard, Arduino_Address); 
    }

    //---Methods to call---\\
    public void ALT_BLUE_YELLOW() {
        Responce.write(Arduino_Address, LEDStatus.ALT_BLUE_YELLOW.getLEDStatus());
    } 

    public void PATTERN_OFF() {
        Responce.write(Arduino_Address, LEDStatus.PATTERN_OFF.getLEDStatus());
    } 

    public void PATTERN_SEND() {
        Responce.write(Arduino_Address, LEDStatus.PATTERN_SEND.getLEDStatus());
    } 

    public void BLUE() {
        Responce.write(Arduino_Address, LEDStatus.BLUE.getLEDStatus());
    } 

    public void GREEN() {
        Responce.write(Arduino_Address, LEDStatus.GREEN.getLEDStatus());
    } 

    public void YELLOW() {
        Responce.write(Arduino_Address, LEDStatus.YELLOW.getLEDStatus());
    } 

    public void RED() {
        Responce.write(Arduino_Address, LEDStatus.RED.getLEDStatus());
    } 

    public void SCROLLING_RAINBOW() {
        Responce.write(Arduino_Address, LEDStatus.SCROLLING_RAINBOW.getLEDStatus());
    } 

    public void FWD_SCROLL_BLUE_YELLOW() {
        Responce.write(Arduino_Address, LEDStatus.FWD_SCROLL_BLUE_YELLOW.getLEDStatus());
    } 

    public void RVS_SCROLL_BLUE_YELLOW() {
        Responce.write(Arduino_Address, LEDStatus.RVS_SCROLL_BLUE_YELLOW.getLEDStatus());
    } 
}  
