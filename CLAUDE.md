# CLAUDE.md - JavaLearning 项目指南

本项目是基于 Java 语言的核心特性与进阶知识学习/教学代码库。包含面向对象、核心类库、高精度计算、设计模式及实用工具的系列 Demo 与原理总结。

---

## 1. 项目概览

- **项目类型**：Java 教学与学习代码库（纯标准库实现，零外部第三方依赖）。
- **语言版本**：Java 17 / Java 21（项目配置支持 JDK 21，兼容 JDK 17+ 语法特性）。
- **工程结构**：原生 IntelliJ IDEA 项目结构（`.iml` 管理），源码位于 `first-step/src/`。
- **命名空间**：`com.betterchinglish.demoXX_<topic>`。

---

## 2. 目录与知识体系结构

源码主目录位于 `first-step/src/`，按知识点演进编号分类：

```
first-step/src/
├── Main.java                                 # 顶层快速启动/临时测试入口
└── com/betterchinglish/
    ├── demo00_helloworld/                    # 基础输出与基础类型工具类 (HelloWorld, TypeUtils)
    ├── demo01_Class/                         # 类与对象、静态成员/方法、工具类设计 (Student, ArrayUtil, Test)
    ├── demo02_extends/                       # 继承机制与多层继承 (ReadMe)
    ├── demo03_polymorphic/                   # 多态机制、父类引用指向子类对象、动态绑定 (Person, Student, Teacher, Test)
    ├── demo04_final/                         # final 关键字用法（常量、防重写、防继承） (Test)
    ├── demo05_codeBlock/                     # 静态代码块与构造代码块执行顺序 (Person, Test)
    ├── demo06_abstract/                      # 抽象类与抽象方法 (Person, Student, Test)
    ├── demo07_abstract_template/             # 模板方法设计模式实战 (People, Student, Teacher, Test)
    ├── demo08_interface/                     # 接口定义、多实现、默认方法与常量 (Interface1, Interface2, ImplClass, Test)
    ├── demo09_interface_adapter/             # 接口适配器设计模式（缺省适配器模式） (Interface1Adapter, MyClass, Test)
    ├── demo09_Math/                          # java.lang.Math 数学运算工具核心方法 (Test)
    ├── demo10_innerClass/                    # 内部类：成员内部类、静态内部类 (Car, Test)
    ├── demo10_System/                        # java.lang.System 底层交互与数组拷贝 (Test)
    ├── demo11_annoymousInnerClass/           # 匿名内部类与函数式接口替代 (Animal, Interface1, Test)
    ├── demo11_Runtime/                       # java.lang.Runtime 运行环境与单例模式 (Test)
    ├── demo12_Object/                        # Object 根类核心方法重写（equals, hashCode, toString, clone） (Student, Test)
    ├── demo13_BigInteger/                    # BigInteger 任意精度整数及其存储运算原理 (Test)
    ├── demo14_BigDecimal/                    # BigDecimal 任意精度浮点数、舍入模式与底层模型 (Test)
    ├── demo15_Regex/                         # 正则表达式与 Pattern / Matcher 匹配/提取/替换 (Test)
    └── demo16_Collection/                    # Collection 单列集合体系全套实战教学与 fail-fast 避坑 (Test, D01-D06)
```

根目录下 `readme.md` 包含重点主题的深度原理总结（如 `BigDecimal` 内部模型 `unscaledValue × 10^(-scale)`、正则表达式分组贪婪懒惰等）。

---

## 3. 构建与运行指南

本项目无需 Maven / Gradle 包装器，既可在 IDE 中直接运行，也可通过标准 Java CLI 编译运行。

### 3.1 IDE 运行（推荐）
- 使用 IntelliJ IDEA 打开项目根目录。
- 确保 Project SDK 配置为 JDK 17 或 JDK 21。
- 在任意包下的 `Test.java` 或 `Main.java` 中点击 `main` 方法旁的绿色运行按钮即可执行。

### 3.2 命令行编译与运行

在项目根目录下执行以下命令：

```bash
# 创建编译输出目录
mkdir -p out

# 编译指定 Demo（以 demo14_BigDecimal 为例）
javac -d out -encoding UTF-8 -sourcepath first-step/src first-step/src/com/betterchinglish/demo14_BigDecimal/*.java

# 运行已编译的 Demo
java -cp out com.betterchinglish.demo14_BigDecimal.Test

# 全量编译所有 Demo
javac -d out -encoding UTF-8 -sourcepath first-step/src $(find first-step/src -name "*.java")

# 运行主入口 Main
java -cp out Main

# 清理编译输出
rm -rf out
```

### 3.3 单文件快速运行（Java 11+）
对于无复杂跨包依赖的独立测试文件：
```bash
java first-step/src/Main.java
```

---

## 4. 代码规范与开发指南

### 4.1 包与类命名规范
- **包命名**：遵循 `com.betterchinglish.demo<序号>_<主题关键字>` 格式（例如 `demo16_Generics`、`demo17_Collections`），保持有序递进。
- **类命名**：
  - 测试驱动/入口类统一定名为 `Test.java`，包含 `public static void main(String[] args)`。
  - 领域实体类采用大驼峰命名（如 `Student.java`, `Person.java`）。
  - 辅助工具类以 `Util` 结尾（如 `ArrayUtil.java`, `TypeUtils.java`），工具类构造器应私有化，方法声明为 `static`。
  - 接口以 `Interface` 或特征命名，适配器采用 `*Adapter` 后缀。

### 4.2 教学化注释风格
项目以教学与知识积累为导向，代码中应包含详尽的教学注释：
- **类注释**：说明该技术点的所属包、核心解决的问题、核心原理与注意事项。
- **分段演示**：在 `main` 方法中采用清晰的分段分隔线（如 `/* ================= 1. xxx ================= */`），标明功能、入参、出参及注意要点。
- **易错点演示**：主动演示典型反例及后果（如 `double` 精度丢失、`divide` 未指定舍入抛异常、`equals` 与 `compareTo` 区别等），并使用 `try-catch` 捕获预期异常避免中断。
- **原理补充方法**：复杂主题可在类底部提供专用的 `printPrinciples()` 私有静态方法打印底层原理剖析。

### 4.3 理论与文档同步
- 当新增或深入展开某一重难点知识模块时，除了编写代码与测试，需在根目录的 `readme.md` 中同步追加 Markdown 格式的原理和表格速查指南。

---

## 5. Git 与提交规范

- **忽略规则**：`.idea/`, `out/`, `.codebuddy` 已配置在 `.gitignore` 中，禁止提交编译输出目录与本地私有 IDE 配置。
- **提交信息**：
  - 遵循简洁直观的主题分类，例如：
    - `feat: 添加泛型基础示例` 或中文主题 `泛型基础`
    - `docs: 完善 BigDecimal 原理说明`
    - `fix: 修复 demo08 接口冲突示例`
    - `chore: 更新 .gitignore`
