package homework;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Decoder {

	private static final String message = "CUgKBRECERoQTBFIT14XAAQVHURHEVUMFhweBBUOFg4WUlVZVxcSAAwGBlQWSFVQVQQSDwwZRQFI WUpSRh0HABlUFgYbHBdGWElEClIaBhwGFwwRBxdMEUhPXgUcDRsKCA5VVUNZVwAAFgsKH0JVT0NQ VRIVDwZMHVJIHx8dRlRTQ0xGGwFYVw8=";
	private static final String key = "roypratick1";

	public static void main(String[] args) {

		byte[] decodedBytes = Base64.getMimeDecoder().decode(message.getBytes(StandardCharsets.UTF_8));
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < decodedBytes.length; i++) {
			output.append((char) (key.charAt(i % key.length()) ^ decodedBytes[i]));
		}
		System.out.println(output);
	}
}
