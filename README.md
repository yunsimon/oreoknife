# oreoknife
这是一个类似butterknife的简单实现，通过注解的形式，简化代码结构

基础原理：<br>
1.利用AnnotationProcessor在编译阶段生成辅助代码，从而大大减少实际使用时的性能损耗<br>
2.在程序运行阶段，调用前面生成的辅助代码，实现注解定义的功能<br>

基于oreoknife，可扩展实现自定义需求
