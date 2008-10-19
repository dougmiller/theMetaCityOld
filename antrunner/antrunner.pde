int orangePin = 13;
int inPin = 7;

int greenPin = 8;
int greenPin2 = 9;

int redPin = 11;
int redPin2 = 12;

int timeout = 5000; /* Timout for waiting for the python script to tell us it is running the build. If it does not 
 respond in time then it is assumed that it is not connected and the build is abandoned*/

int isProcessing = false; // Set when the script confirms that it is processing the build
int hasRun = false; // Used to only write to the serial port once when the button is pushed/held down
int runOnce = false; // Used to check that dont start listening for serial if the button is held down

void setup() {
  pinMode(inPin, INPUT);
  pinMode(orangePin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  pinMode(greenPin2, OUTPUT);
  pinMode(redPin, OUTPUT);
  pinMode(redPin2, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  if (digitalRead(inPin) == HIGH){
    if (!hasRun){
      Serial.println("go");  // Tell the python script to start processing the build
      hasRun = true;         // The line was been written dont do again for this time the button is pushed 
    }

    if (hasRun && !runOnce){  // If button has been pushed and it is the first loop for the button push
      for (int i = 0; i < timeout; i += 1000){  // Check that the script is running and will respond in time 
        if (Serial.available() > 0){     
          if (Serial.read() == 'p') { //p for processing
            isProcessing = true;
            break;
          } // else it remains false and we abort
        }
        // waiting effects
        digitalWrite(orangePin,HIGH);
        delay(500);
        digitalWrite(orangePin,LOW);
        delay(500);         
      } 

      if (isProcessing) {      
        while (Serial.available() <= 0){
          // Fancy waiting for success or failure effects
          digitalWrite(orangePin,HIGH);
          delay(200);
          digitalWrite(orangePin,LOW);
          delay(200); 
        }         

        // Processing is finished, check for 1 or anything else; success or failure respectively
        if (Serial.read() == '1'){    // Green pin, as all was good
          digitalWrite(greenPin,HIGH);
          digitalWrite(greenPin2,HIGH);
          delay(2000);
          digitalWrite(greenPin,LOW);
          digitalWrite(greenPin2,LOW);
        } 
        else {                  // Something went wrong in the build
          // Begin cool effects
          for (int i = 1; i < 10; i++){
            digitalWrite(redPin,HIGH);
            delay(100);
            digitalWrite(redPin2,HIGH);
            delay(100);
            digitalWrite(redPin,LOW);
            delay(100);
            digitalWrite(redPin2,LOW);
          }
        }
        runOnce = true;  
      }
    }
  } 
  else {
    isProcessing = false; // reset so that it wont jump to processing if hte script is not responding
    hasRun = false;  // reset so that the line will be written again
    runOnce = false; // reset so that it can run the check again
  }
}
