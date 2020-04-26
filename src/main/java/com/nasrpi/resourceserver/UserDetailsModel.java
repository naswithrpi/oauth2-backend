/**
 * Copyright nasrpi 2020
 */

package com.nasrpi.resourceserver;

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
public class UserDetailsModel {
	private String userName;
	
	private String email;
	
	private String mobile;
	
	private String dateOfBirth;
}
