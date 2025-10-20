package com.webgram.dgpsn.models.responses;

import lombok.Builder;
import lombok.Data;
import com.webgram.dgpsn.models.CadreLogiqueDTO;

import java.util.List;

@Data
@Builder
public class TreeNode {
    private CadreLogiqueDTO data;
    private List<TreeNode> children;
    private Boolean partialSelected = true;
}
