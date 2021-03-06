# KBEXTENSIONS  TAG: 1.1.5

KBEXTENSIONS is an android library which provides an easy way to use collections of common functions and functionalities which we use in every projects and write the same code again and again.

## How to add to your project

What things you need to install the software and how to install them

Step 1: Add it in your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

```

Step 2: Add the dependency:-

```
dependencies 
{
	implementation 'com.github.kanakboro:kbextensions:Tag'
}
	
```

### How to use

It's very simple and easy to use all the extension functions into your project :

```
yourContext.showToastMessage(yourStringMessageHere)

YourDataToShow.showLogMessage(yourTag,logType)

YourTextView.strikeTextView()

YourTextView.setMarque()

```

Convert date from one format to another  :

```
getFormattedDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'","dd-MM-yyyy",Locale.getDefault(), TimeZone.getTimeZone("UTC"))

YourMilliDate.getDateFromMilli("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.getDefault(), TimeZone.getTimeZone("UTC"))

```

Easy way to use validator into your project  :

```
 val validator = Validation.create(context.get()).apply {
	isEmpty(yourStringValue,YourStringErrorMessage)
	areEqual(yourStringValue1, yourStringValue2 ,YourStringErrorMessage)
	areNotEqual(yourStringValue1, yourStringValue2 ,YourStringErrorMessage)
}

if (validator.isValid()) {
   doStuff()
}

```

### Available Features

To check all the feature and functions available for KBEXTENSIONS, please visit the [here](https://sites.google.com/view/kbextensions).
