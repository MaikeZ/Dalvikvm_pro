# Android Dalvikvm
### dalvikvm:用来后台创建一个虚拟机运行指定的java类
	核心：dalvikvm –cp dex化jar路径 需要执行的主函数类完整类名 主函数参数
	主函数多个参数的空格隔开

### 前置说明
	ANDROID_DATA默认为/data
	执行dalvikvm后需要生成一个dex文件存放在$ANDROID_DATA/dalvik-cache 目录下
	所以执行时必须对目录要有写入权限。一般情况下，程序无root权限。所以无法读写默认的/data/ dalvik-cache区。只能存在应用本身的缓存区域。/data/data/包名/ 目录下。
	执行export ANDROID_DATA=/data/data/包名/ 修改环境变量，紧接着执行dalvikvm命令。代码中执行时一定要用逗号隔开，同一句执行，这样环境变量的修改才有效。
	执行完全后发现/data/data/包名/dalvik-cache 目录下生成对应的dex文件。并且主函数被执行。
	普通jar dx流程
	找到SDK的目录/build-tools/android-x.x/ 不同的build版本目录名可能有所区别，目录下面有dx工具就可以。
	执行dx --dex --output=out.jar in.jar 得到out.jar （这里可以选择用工具混淆）。	应用中一般携带jar，作为放在assets是一种方法，但是容易被直接解压拿走。后想到另外一种方法，将文件转成byte数组。要用的时候还原成文件。这样文件更加安全。而且后面如果代码混淆后可以进一步增加安全系数
		
### 项目组成：
	Android项目 一个简单apk 展现在手机上。DalvikvmDemo
	Java 项目 打包需要执行的main()方法。jar_Demo
> jar_Demo：
> 
> > 1.生成jar包
> 
> > > 先说Java项目一个主方法MainMethod 提供一个主函数，里面简单的写个打印，这里为了直观写个日志到SD卡上。
> 
> > > 鼠标放在package：net.maikeZ.demo上右键Export成一个jar包。比如in.jar 存在本地。
> 
> > 2.dx化jar包
> 
> > > 拷贝in.jar到dx目录下。命令行执行dx.bat --dex --output=out.jar in.jar 生成out.jar
> 
> > 3.生成byte[]类型存储
> 
> > > 方法见net.maike.common.BytesByJar 我这里将上一步生成的out.jar随便丢在本地的D盘test文件夹下.Eclipse里面执行BytesByJar 主函数。在bytes 目录下生成了byte01.java
> 
> > > （如果jar比较大的话，会生成多个byteXX.java）
> 
> DalvikvmDemo
> 
> > 将生成的byte01.java 拷贝到net.maikeZ.bytes 下。
> 
> > 如果是多个的话 记得在net.maikeZ.util.GetFile.java 里面添加。
> 
> > 运行生成apk即可。
