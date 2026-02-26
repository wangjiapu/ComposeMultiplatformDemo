package org.examp.project.domain.algorithm

import androidx.compose.runtime.rememberUpdatedState
import org.examp.project.domain.core.LinkedListCompat
import org.examp.project.domain.core.TreeNode

class Solution02 {
    fun preorderTraversal(root: TreeNode?): IntArray {
        val result = mutableListOf<Int>()
        traversal(root, result)
        return result.toIntArray()
    }

    fun inorderTraversal(root: TreeNode?): IntArray {
        val result = mutableListOf<Int>()
        traversal2(root, result)
        return result.toIntArray()
    }

    fun postorderTraversal(root: TreeNode?): IntArray {
        val result = mutableListOf<Int>()
        traversal3(root, result)
        return result.toIntArray()
    }

    fun levelOrder(root: TreeNode?): Array<IntArray> {
        val result = mutableListOf<IntArray>()
        if (root == null) {
            return result.toTypedArray()
        }
        /**
         * 问：Kotlin 中为什么用 ArrayDeque 实现队列，而不是 LinkedList？
         * 答：ArrayDeque 是基于动态数组实现的队列，LinkedList 是基于双向链表实现的。ArrayDeque 的入队 / 出队操作效率更高（数组的随机访问特性），
         * 且 Kotlin/Java 中推荐用 ArrayDeque 作为队列 / 栈的首选实现，仅在需要链表的特殊特性（如中间插入 / 删除）时才用 LinkedList。
         */
        val queue = ArrayDeque<TreeNode>()
        queue.add(root)
        while (queue.isNotEmpty()) {
            val size = queue.size
            val level = mutableListOf<Int>()
            for (i in 0 until size) {
                val node = queue.removeFirst()
                level.add(node.`val`)
                node.left?.let {
                    queue.add(it)
                }
                node.right?.let {
                    queue.add(it)

                }
            }
            // 如果要求自底向上的层序遍历（从最后一层到第一层，LeetCode 107），该怎么修改代码？
            //答：核心逻辑完全不变，仅需在将当前层结果加入最终结果时，添加到结果集的头部（而非尾部）。Kotlin 中可将 result.add(currentLevel) 改为 result.add(0, currentLevel)
            result.add(level.toIntArray())
        }
        return result.toTypedArray()
    }

    /**
     * 之字形答应二叉树
     */
    fun printTree(pRoot: TreeNode?): Array<IntArray> {
        val result = mutableListOf<IntArray>()
        if (pRoot == null) {
            return result.toTypedArray()
        }
        val queue = ArrayDeque<TreeNode>()
        queue.add(pRoot)
        var leftToRight = true
        while (queue.isNotEmpty()) {
            val size = queue.size
            val level = mutableListOf<Int>()
            for (i in 0 until size) {
                val node = queue.removeFirst()
                level.add(node.`val`)
                node.left?.let {
                    queue.add(it)
                }
                node.right?.let {
                    queue.add(it)
                }
            }
            if (leftToRight) {
                result.add(level.toIntArray())
            } else {
                result.add(level.reversed().toIntArray())
            }
            leftToRight = !leftToRight
        }
        return result.toTypedArray()
    }

    /**
     * 二叉树的最大深度
     */

    fun maxDepth(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        val left = maxDepth(root.left)
        val right = maxDepth(root.right)
        return maxOf(left, right) + 1
    }

    /**
     * 二叉树中和为某一值的路径(一)
     */
    fun hasPathSum(root: TreeNode?, sum: Int): Boolean {
        if (root == null) {
            return false
        }
        if (root.`val` - sum == 0 && root.left == null && root.right == null) {
            return true
        }
        val tempSum = sum - root.`val`
        return hasPathSum(root.left, tempSum) || hasPathSum(root.right, tempSum)
    }

    /**
     *对称二叉树
     */
    fun isSymmetrical(pRoot: TreeNode?): Boolean {

        return pRoot == null || checkSymmetric(pRoot.left, pRoot.right)
    }

    // 递归实现
    fun checkSymmetric(left: TreeNode?, right: TreeNode?): Boolean {
        if (left == null && right == null) {
            return true
        }
        if (left == null || right == null) {
            return false
        }
        if (left.`val` != right.`val`) {
            return false
        }
        return checkSymmetric(left.left, right.right) && checkSymmetric(left.right, right.left)
    }

    // 非递归实现
    fun isSymmetricIterative(pRoot: TreeNode?): Boolean {
        if (pRoot == null) {
            return true
        }
        val queue = ArrayDeque<TreeNode?>()
        queue.add(pRoot.left)
        queue.add(pRoot.right)
        while (queue.isNotEmpty()) {
            val left = queue.removeFirst()
            val right = queue.removeFirst()
            if (left == null && right == null) {
                continue
            }
            if (left == null || right == null) {
                return false
            }
            if (left.`val` != right.`val`) {
                return false
            }
            queue.add(left.left)
            queue.add(right.right)
            queue.add(left.right)
            queue.add(right.left)
        }
        return true
    }

    private fun traversal3(root: TreeNode?, result: MutableList<Int>) {
        if (root == null) {
            return
        }
        traversal(root.left, result)
        traversal(root.right, result)
        result.add(root.`val`)
    }

    private fun traversal2(root: TreeNode?, result: MutableList<Int>) {
        if (root == null) {
            return
        }
        traversal(root.left, result)
        result.add(root.`val`)
        traversal(root.right, result)
    }

    private fun traversal(root: TreeNode?, result: MutableList<Int>) {
        if (root == null) {
            return
        }
        result.add(root.`val`)
        traversal(root.left, result)
        traversal(root.right, result)
    }
}