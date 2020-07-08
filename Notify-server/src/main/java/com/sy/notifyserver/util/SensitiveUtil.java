package com.sy.notifyserver.util;

import com.sy.basis.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 敏感词
 * @author wangxiao
 * @since 1.1
 */
public class SensitiveUtil {


    private static final String DEFAULT_REPLACEMENT = "***";

    private TrieNode rootNode = new TrieNode();


    /**
     * 判断是否是一个符号
     */
    private boolean isSymbol(char c) {
        int ic = (int) c;
        return !StringUtil.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }


    /**
     * 过滤敏感词
     */
    public String filter(String text) {
        if (StringUtil.isBlank(text)) {
            return text;
        }
        StringBuilder result = new StringBuilder();
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        while (position < text.length()) {
            char c = text.charAt(position);
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                result.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            } else if (tempNode.isEnd()) {
                result.append(DEFAULT_REPLACEMENT);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            } else {
                ++position;
            }
        }
        result.append(text.substring(begin));

        return result.toString();
    }

    private void addWord(String lineTxt) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < lineTxt.length(); ++i) {
            Character c = lineTxt.charAt(i);
            if (isSymbol(c)) {
                continue;
            }
            TrieNode node = tempNode.getSubNode(c);
            if (node == null) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }
            tempNode = node;
            if (i == lineTxt.length() - 1) {
                tempNode.setEnd(true);
            }
        }
    }



    private final class TrieNode {
        private boolean end;
        private final Map<Character, TrieNode> subNodes = new HashMap<>();

        void addSubNode (Character key,TrieNode subNode) {
            subNodes.put(key,subNode);
        }
        TrieNode getSubNode (Character key) {
            return subNodes.get(key);
        }

        boolean isEnd() {
            return end;
        }

        void setEnd(boolean end) {
            this.end = end;
        }

        int getSubNodesCount () {
            return subNodes.size();
        }


    }
}
