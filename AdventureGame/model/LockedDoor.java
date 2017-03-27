package swen221.lab3.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class LockedDoor extends Door{
	private int screteCode;
	private boolean isLocked;
	public LockedDoor(Room oneSide, Room otherSide,int code){
		super(oneSide, otherSide);
		this.screteCode = code;
		this.isLocked = true;
	}
	@Override
	public String[] getActions(){
		return new String[]{"Unlock","Lock","Enter"};
		}
	@Override
	public boolean performAction(String action, Player player) {
		Room r = player.getLocation();
		//store all found keys to a list
		ArrayList<Integer> codes = new ArrayList<>();
		for(Item item : player.getInventory()){
			if(item instanceof Key){
				int code = ((Key) item).getCode();
				codes.add(code);
			}
		}
		//check if find the screteCode
		boolean isFoundKey = false;
		if(codes.contains(this.screteCode)){
			isFoundKey = true;
		}else{
			isFoundKey = false;
		}
		//check player action
		if(action.equals("Lock")){
			if(isFoundKey){
				return this.isLocked = true;				
			}else{
				return false;
			}
			
		}else if(action.equals("Unlock")){
			if(isFoundKey){
				this.isLocked = true;
				return true;
			}else{
				return false;
			}
		}else if(action.equals("Enter")){
			if(isLocked){
				
				return false;
			}else{
				//from Door class
				if(r == oneSide()) {
					player.setLocation(otherSide());
				} else {
					player.setLocation(oneSide());
				}
				return true;
			}
		}else{
			throw new IllegalArgumentException("Unknon action: "+action);
			
		}
	}
		
	@Override
	public String getDescription() {
		String s = "A door between \"" + this.oneSide().getDescription() +"\" and \""+ this.otherSide().getDescription() + "\"; it is ";
		if(isLocked){
			return s + "locked";
		}else{
			return s + "unlocked";
		}
	}
	public int getCode(){
		return this.screteCode;
	}
	public void unlockDoor(boolean bool){
		this.isLocked = bool;
		
	}
	/**
	 * Return the room on one side of the door
	 * 
	 * @return
	 */
	public Room oneSide() { return oneSide; }
	
	/**
	 * Return the room on the other side of the door
	 * 
	 * @return
	 */
	public Room otherSide() { return otherSide; }	

	@Override
	public void draw(Graphics g) {
		
	}
}
