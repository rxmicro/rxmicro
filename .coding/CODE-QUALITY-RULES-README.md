# Common code quality rules:

## Common restrictions

* Source code max line length: `140` (`checkstyle.LineLength`)
* Java file max lines: `1000` (`checkstyle.JavaFileLength`)
* XML file max lines: `500` (`checkstyle.XmlFileLength`)
* Properties file max lines: `300` (`checkstyle.PropertiesFileLength`)

## Class restrictions

* Max imports per class: `100` (`PMD.ExcessiveImports`)
* Min class name length: `4` (`PMD.ShortClassName`)
* Anonymous classes max lines: `30` (`checkstyle.AnonInnerLength`)

## Variable restrictions

* Max fields per class: `20` (`PMD.TooManyFields`)
* Fields, formal arguments, or local variable min name length: `2` (`PMD.ShortVariable`)
* Fields, formal arguments, or local variable max name length: `49` (`PMD.LongVariable`)
* Distance between declaration of variable and its first usage: `3` (`checkstyle.VariableDeclarationUsageDistance`)

### Method restrictions

* Min method name length: `2` (`PMD.ShortMethodName`)
* Parameters limit:
    * Constructor: `40` (`checkstyel.ConstructorParameterNumber`)
    * Method: `8` (`checkstyel.MethodParameterNumber`)
    * Record constructor: `20` (`checkstyel.RecordComponentNumber`)
* Throws count: `3` (`checkstyle.ThrowsCount`)
* Method body limit:
    * ConstructorStatementCount: `20` (`checkstyle.ConstructorStatementCount`)
    * MethodStatementCount: `40` (`checkstyle.MethodStatementCount`)
    * InstanceInitStatementCount: `0` (`checkstyle.InstanceInitStatementCount`)
    * StaticInitStatementCount: `40` (`checkstyle.StaticInitStatementCount`)
* Return count:
    * LambdaReturnCount: max=`3`, maxForVoid=`1`
    * ConstructorReturnCount: `0`
    * MethodReturnCount: `6`
* Methods limit per class:
    * Class/interface : (`checkstyle.TypeMethodCount`)
    * Enum : (`checkstyle.EnumMethodCount`)
    * Annotation : (`checkstyle.AnnotationMethodCount`)
    * Record : (`checkstyle.RecordMethodCount`)

## Complexity

* Cognitive: `42` (`PMD.CognitiveComplexity`)
* Cyclomatic:

    * classReportLevel: `80` (`PMD.CyclomaticComplexity`)

    * methodReportLevel: `25` (`PMD.CyclomaticComplexity`)

* CouplingBetweenObjects: `40` (`PMD.CouplingBetweenObjects`)
* NPath: `200` (`PMD.NPathComplexity`)
* NcssCount:

    * classReportLevel: `1500` (`PMD.NcssCount`)

    * methodReportLevel: `60` (`PMD.NcssCount`)

* Boolean expressions: `4` (`checkstyle.BooleanExpressionComplexity`)
* ClassFanOutComplexity: `35` (`checkstyle.ClassFanOutComplexity`)
