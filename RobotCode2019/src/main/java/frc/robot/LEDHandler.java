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

public class LEDHandler {

    private int Arduino_Address = 0; //Init the aruino I2C address
    //---- Object Declarations ----\\
	private static I2C Responce; //Create I2C object
    public LEDHandler(int Arduino_Address_Value){
        Arduino_Address = Arduino_Address_Value; //Allow arduino address to change
        Responce = new I2C(Port.kOnboard, Arduino_Address); //Declare arduino address
    }

    // -- Variable Declarations --\\
    private enum LEDStatus{
        PATTERN_OFF(0), //Clear
        PATTERN_SEND(1),
        ALT_BLUE_YELLOW(3),
        BLUE(4),
        GREEN(5),
        YELLOW(6),
        RED(7),
        SCROLLING_RAINBOW(8),
        FWD_SCROLL_BLUE_YELLOW(9),
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
