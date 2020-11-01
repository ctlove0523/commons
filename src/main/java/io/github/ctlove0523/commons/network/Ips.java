package io.github.ctlove0523.commons.network;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import io.github.ctlove0523.commons.Predications;

public class Ips {
	public static InetAddress getIpByNetworkInterfaceName(String name) {
		Predications.stringNotNull(name, "interface name must not be null");
		try {
			for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
				NetworkInterface networkInterface = interfaces.nextElement();
				Set<String> virtualIpSet = new HashSet<>();
				for (Enumeration<NetworkInterface> virtualInterfaces = networkInterface.getSubInterfaces(); virtualInterfaces.hasMoreElements(); ) {
					for (Enumeration<InetAddress> inetAddress = virtualInterfaces.nextElement().getInetAddresses(); inetAddress.hasMoreElements(); ) {
						virtualIpSet.add(inetAddress.nextElement().getHostAddress());
					}
				}

				if (name.equals(networkInterface.getName())) {
					for (Enumeration<InetAddress> inetAddress = networkInterface.getInetAddresses(); inetAddress.hasMoreElements(); ) {
						InetAddress address = inetAddress.nextElement();
						if (validNetworkInterfaceAddress(address, virtualIpSet)) {
							return address;
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static boolean validNetworkInterfaceAddress(InetAddress address, Set<String> virtualIps) {
		return !(address instanceof Inet6Address) && !(address.isLoopbackAddress())
				&& !virtualIps.contains(address.getHostAddress());
	}
}
