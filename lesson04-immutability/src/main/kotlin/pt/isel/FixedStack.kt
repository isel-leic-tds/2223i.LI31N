package pt.isel

class FixedStack<T>(private val head : Node<T>? = null) : Stack<T>{

    /*
    private val head : Node<T>?

    constructor(){
        head = null
    }
    constructor(node: Node<T>?){
        head = node
    }
    constructor(item: T, node: Node<T>?){
        head = Node(item, node)
    }
    */

    override fun push(item: T): Stack<T> {
        return FixedStack(Node(item, head))
    }

    override fun peek(): T {
        return head?.item ?: throw NoSuchElementException()
    }

    override fun isEmpty(): Boolean {
        return head==null
    }

    override fun pop(): Stack<T> {
        if(head == null) throw NoSuchElementException()
        return FixedStack(head.next)
    }
}
