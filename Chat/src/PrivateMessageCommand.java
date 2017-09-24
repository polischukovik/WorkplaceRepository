import java.net.ProtocolException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PrivateMessageCommand extends Command{
	private static IdGenerator generator = new IdGenerator();
	
	String recipientId;
	String text = "";
	
	public PrivateMessageCommand(String senderId, String recipientId, String text, long timestamp) {
		super(generator.next(), UserPerson.getById(senderId));
		this.recipientId = recipientId;
		this.text = text;
	}
	
	public String getRecipientId() {
		return recipientId;
	}
	
	public String getText() {
		return text;
	}
	
	public static PrivateMessageCommand valueOf(Command cmd) throws ProtocolException{
		String value = cmd.getValue();
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		PrivateMessageCommand command = gson.fromJson(value, PrivateMessageCommand.class);
		command.validate();
		return command;
	}
	
	@Override 
	public void validate() throws ProtocolException{
		if(this.sender == null || this.recipientId == null || this.text == null){
			throw new ProtocolException();
		};
	}

	@Override
	void execute() throws ProtocolException {
		PrivateMessageCommand privateMessage = PrivateMessageCommand.valueOf(this);
		UserPerson recipient = UserPerson.getById(privateMessage.getRecipientId());
		if (recipient == null){
			throw new IllegalArgumentException("Recipient user was not found: id=" + privateMessage.getRecipientId());
		}
		recipient.send(privateMessage);
	}	
	
	
}
