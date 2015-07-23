package com.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class ObjectUtils
{
	static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

	public static Object read(String fileName)
	{
		return objRead(fileName);
	}

	public static void write(Object object, String fileName)
	{
		objWrite(object, fileName);
	}

	private static Object objRead(String fileName)
	{
		try
		{
			FileInputStream fin = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fin);
			Object object = ois.readObject();
			ois.close();
			return object;
		}
		catch (Exception e)
		{
			logger.error("Error in read", e);
			return null;
		}

	}

	private static void objWrite(Object object, String fileName)
	{
		try
		{
			FileOutputStream fout = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(object);
			oos.close();
			logger.info("Writing to file: " + fileName);
		}
		catch (Exception e)
		{
			logger.error("Error in write", e);
		}

	}

	private static final Gson JSON_MARSHALLER = new com.google.gson.GsonBuilder().setDateFormat("MM-dd-YYYY'T'HH:mm:ss").registerTypeAdapter(Serializable.class, new JsonDeserializer<Serializable>()
	{
		@Override
		public Serializable deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException
		{
			// TODO
			return null;
		}
	}).enableComplexMapKeySerialization().create();

	public static <T> T fromJsonWithClass(String jsonString, Class<T> clazz)
	{
		try
		{
			T dataObject = JSON_MARSHALLER.fromJson(jsonString, clazz);
			return dataObject;
		}
		catch (Exception ex)
		{
			logger.error("Unable to deserialize json string [" + jsonString + "] to [" + clazz.getName() + "] - " + ex.getMessage());
			return null;
		}
	}

	public static <T> T fromJson(String jsonString, TypeToken<T> type)
	{
		try
		{
			T dataObject = JSON_MARSHALLER.fromJson(jsonString, type.getType());
			return dataObject;
		}
		catch (Exception ex)
		{
			logger.error("Unable to deserialize json string [" + jsonString + "] to [] - " + ex.getMessage());
			return null;
		}
	}

	public static <T> String toJsonPretty(T objectData)
	{
		try
		{
			String jsonW = JSON_MARSHALLER.toJson(objectData, objectData.getClass().getGenericSuperclass());

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(jsonW);
			String prettyJsonString = gson.toJson(je);

			return prettyJsonString;
		}
		catch (Exception ex)
		{
			logger.error("Unable to serialize  [" + objectData.getClass().getName() + "] - " + ex.getMessage());
			return null;
		}
	}

	public static <T> String toJson(T objectData)
	{
		try
		{
			String jsonW = JSON_MARSHALLER.toJson(objectData, objectData.getClass().getGenericSuperclass());

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(jsonW);
			String prettyJsonString = gson.toJson(je);

			return prettyJsonString;
		}
		catch (Exception ex)
		{
			logger.error("Unable to serialize  [" + objectData.getClass().getName() + "] - " + ex.getMessage());
			return null;
		}
	}

	public static <T> String toJsonBase(T objectData)
	{
		try
		{
			String jsonW = JSON_MARSHALLER.toJson(objectData, objectData.getClass());
			return jsonW;
		}
		catch (Exception ex)
		{
			logger.error("Unable to serialize  [" + objectData.getClass().getName() + "] - " + ex.getMessage());
			return null;
		}
	}

	// e.g. rowLite = (RowLite) ObjectUtils.jsonRead("test.json", new TypeToken<RowLite>(){ });
	public static <T> T fromJsonFile(String fileName, TypeToken<T> typeToken) throws Exception
	{
		String json = (String) ObjectUtils.fromStringFile(fileName);
		return fromJson(json, typeToken);
	}

	public static void toJsonFile(Object object, String fileName) throws Exception
	{
		String json = toJsonPretty(object);
		ObjectUtils.toStringFile(json, fileName);
	}

	public static String fromStringFile(String fileName) throws Exception
	{
		StringBuffer allLines = new StringBuffer();
		String sCurrentLine;
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		while ((sCurrentLine = br.readLine()) != null)
		{
			// allLines += sCurrentLine;
			allLines.append(sCurrentLine);
		}
		return allLines.toString();
	}

	public static void toStringFile(String object, String fileName) throws Exception
	{
		FileWriter fw = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(object);
		bw.close();
		System.out.println("Writing to file: " + fileName);
	}

	public static String toBase64String(Object obj) throws Exception
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(obj);
		byte[] serialize = b.toByteArray();
		return Base64.encodeBase64String(serialize);
	}

	public static Object fromBase64String(String base64String) throws Exception
	{
		byte[] decodeBase64 = Base64.decodeBase64(base64String);
		ByteArrayInputStream b = new ByteArrayInputStream(decodeBase64);
		ObjectInputStream o = new ObjectInputStream(b);
		Object readObject = o.readObject();
		return readObject;
	}

}
