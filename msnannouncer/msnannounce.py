import msnp
import time

# The class that handles individual chat sessions
class MsnChatListener(msnp.ChatCallbacks):
    # Run when a message is recieved
    def message_recieved(self, passport_id, displayname, text, charset):
        # Send the message for parsing nd formatting
        if NameFunctions.parseAndCheckText(text) == 1:
            self.chat.send_message("Format error: Message is to long.")

# The class for the users session
class MsnListener(msnp.SessionCallbacks):
    def state_changed(self, state):
        if state == msnp.States.ONLINE:
            print 'New state:', state

    # Friend online checker. Checks who is online when logging in.(Dont really care atm)
    def friend_online(self, state, passport_id, display_name):
        print display_name + '(' + passport_id +') has come online'
        
    # Change your display name. 
    def display_name_changed(self, display_name):
        # no idea
        print 'x'
        
    # Automatically add friend to allowed users list
    def friend_added(self, list_, passport_id, display_name, group_id=-1):
        # help(msnp.SessionCallbacks.friend_added)
        print 'v'
        
# The class that handles string parsing related functions.
class NameFunctions():
    # Parse the text to se if it is a useable format
    def parseAndCheckText(text):
        # Split the input into its parts delimitering on "/)
        # Should be in the format "BrodcastMessage / TTL"
        # BroadcastMessage is the message you wish to send to everyone, maximum length is SOMETHIING we will say 10 for now
        # TTY is the time for the message to live in the broadcast, measured in oh i dont know, seconds
        entry = text.split('/')
        # Check that the message is not too long
        if entry[0].length() > 10:
            return 1
        
            

msn = msnp.Session(MsnListener())
msn.login('msnannouncer@hotmail.com', 'password')

while True:
    msn.process()
    time.sleep(1)
