package pt.isel

class MutableStack<T> : Stack<T> {
    private var head : Node<T>? = null

    override fun push(item: T) {
        head = Node(item, head)
    }

    override fun peek(): T {
        return head?.item ?: throw NoSuchElementException()
    }

    override fun isEmpty(): Boolean = head == null

    override fun pop(): T {
        val elem = peek()
        head = head?.next
        return elem
    }

}
