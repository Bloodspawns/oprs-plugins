package com.l2.accountmanager;

import com.microsoft.alm.secret.Credential;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OsrsCredential
{
	private int index;
	private Credential credential;
}
