package com.hsun324.simplebukkit.threading;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MessageableThread extends Thread
{
	private class MessageHandler
	{
		private final Map<String, List<Object>> map = new LinkedHashMap<String, List<Object>>();
		/**
		 * Adds a message to the queue for the specified identifier.
		 * @param identifier the identifier
		 * @param data the message data
		 */
		public void addMessage(String identifier, Object data)
		{
			if(map.containsKey(identifier))
				map.get(identifier).add(data);
			else
			{
				List<Object> list = new ArrayList<Object>();
				list.add(data);
				map.put(identifier, list);
			}
		}
		/**
		 * Fetches the next message in the queue for the specified identifier.
		 * It then removes the message from the queue.
		 * @param identifier the identifier
		 * @return the message, null if there are no more
		 */
		public Object getNextMessage(String identifier)
		{
			if(map.containsKey(identifier))
			{
				List<Object> list = map.get(identifier);
				if(!list.isEmpty())
					return list.remove(0);
			}
			return null;
		}
		/**
		 * Fetches all messages in the queue for the specified identifier.
		 * It then removes all messages from the queue.
		 * @param identifier the identifier
		 * @return the messages, an empty array if there are no more
		 */
		public Object[] getMessages(String identifier)
		{
			if(map.containsKey(identifier))
			{
				List<Object> list = map.get(identifier);
				Object[] objectArray = list.toArray(new Object[0]);
				list.clear();
				return objectArray;
			}
			return new Object[0];
		}
	}
	protected final MessageHandler messageHandler = new MessageHandler();
	public MessageableThread(String name)
	{
		super(name);
	}

	public MessageHandler getMessageHandler()
	{
		return messageHandler;
	}
	
	/**
	 * A convenience method for {@link MessageHandler#addMessage(String, Object)}.
	 * @param identifier the identifier
	 * @param data the message data
	 */
	public void addMessage(String identifier, Object data)
	{
		messageHandler.addMessage(identifier, data);
	}
	/**
	 * A convenience method for {@link MessageHandler#getNextMessage(String)}.
	 * @param identifier the identifier
	 * @return the message, null if there are no more
	 */
	public Object getNextMessage(String identifier)
	{
		return messageHandler.getNextMessage(identifier);
	}
	/**
	 * A convenience method for {@link MessageHandler#getMessages(String)}.
	 * @param identifier the identifier
	 * @return the messages, an empty array if there are no more
	 */
	public Object[] getMessages(String identifier)
	{
		return messageHandler.getMessages(identifier);
	}
}
