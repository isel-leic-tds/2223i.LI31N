package pt.isel

class MutableStack<T> : Stack<T> {
    private var head : Node<T>? = null

    override fun push(item: T) : Stack<T> {
        head = Node(item, head)
        return this
    }

    override fun peek(): T {
        return head?.item ?: throw NoSuchElementException()
    }

    override fun isEmpty(): Boolean = head == null

    override fun pop(): Stack<T> {
        if(head == null) throw NoSuchElementException()
        head = head?.next
        return this
    }

}
