# common-ui
project include  common ui widget

#To get a Git project into your build:
Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.chewu1590:common-ui:1.0.3'
	}

#Thanks
1.[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)