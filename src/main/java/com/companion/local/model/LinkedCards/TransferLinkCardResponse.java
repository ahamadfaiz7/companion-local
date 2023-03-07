package com.companion.local.model.LinkedCards;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class TransferLinkCardResponse implements Serializable {

    private String oldCardIdentifier;
    private String newCardIdentifier;
    private String responseStatus;
}
