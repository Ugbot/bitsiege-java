package soulfoam.arena.main.menu.chat;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;

import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.util.GameUtil;
import soulfoam.arena.net.lobby.LobbyManager;

public class ChatUIPrivateChat {

	private float x, y, width, height;
	private boolean isActive;
	
	private MenuButton sendButton;
	private TextField chatInput;
	
	private ChatUIPrivateObject chatPartner;

	public ChatUIPrivateChat(GameContainer gc, float x, float y) {
		setX(x);
		setY(y);
		setWidth(380);
		setHeight(240);
		
		sendButton = new MenuButton("Send", getX() + 333, getY() + 207, 40, 11);
		
		chatInput = new TextField(gc, Res.bitFont, getX() + 8, getY() + 209, 319, 7);
		chatInput.makeOpaque();
		chatInput.setMaxLength(240);
	}

	public void update(GameContainer gc, int delta) {

		sendButton.update(gc);

		if (!chatInput.hasFocus()) {
			chatInput.setFocus(true);
		}

		if (gc.getInput().isKeyPressed(Input.KEY_ENTER) && !chatInput.getText().trim().isEmpty()) {
			sendChat();
		}
		
		if (sendButton.isClicked()){
			sendChat();
		}
		
	}
	
	public void sendChat(){
		if (chatInput.getText().isEmpty() || chatPartner == null){
			return;
		}
		String message = LobbyManager.getManager().getUserAccount().getUsername() + ": " + chatInput.getText().trim();
		for (String chatTextPart : GameUtil.getStringParts(message, 62)) {
			if (chatTextPart != null) {
				chatPartner.addChatHistory(new ChatUIHistoryMessage(chatTextPart, LobbyManager.getManager().getUserAccount().getNameColor()));
			}
		}

		LobbyManager.getManager().getChatManager().sendPrivateChat(chatPartner.getID(), chatInput.getText().trim());
		chatInput.setText("");
	}

	public void render(GameContainer gc, Graphics g) {

		sendButton.render(g, 2, gc);
		chatInput.render(gc, g);
		
		float spacing = 0;
		if (chatPartner == null){
			return;
		}
		for (ChatUIHistoryMessage message : chatPartner.getChatHistory()){
			Res.bitFont.drawString(x + 2, y + spacing + 8, message.getMessage(), message.getColor());
			spacing += 8;
		}
		
	}

	public void mouseWheelMoved(int m) {
		if (m > 0) { // downward wheel
		
		} else if (m < 0) { // upward wheel
	
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
		chatInput.setAcceptingInput(isActive);
	}

	public MenuButton getSendButton() {
		return sendButton;
	}

	public void setSendButton(MenuButton sendButton) {
		this.sendButton = sendButton;
	}

	public ChatUIPrivateObject getChatPartner() {
		return chatPartner;
	}

	public void setChatPartner(ChatUIPrivateObject chatPartner) {
		this.chatPartner = chatPartner;
	}


}
