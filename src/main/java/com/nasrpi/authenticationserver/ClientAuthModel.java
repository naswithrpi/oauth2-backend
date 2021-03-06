/**
 * Copyright nasrpi 2020
 */

package com.nasrpi.authenticationserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zuilee
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAuthModel {
	private String clientID;
	
	private String redirectionURL;
	
	private String scope;
}
