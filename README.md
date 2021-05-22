# KBEXTENSIONS  TAG: 1.1.1

KBEXTENSIONS is an android library which provides an easy way to use collections of common functions and functionalities which we use in every projects and write the same code again and again.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

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
getFormattedDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'","dd-MM-yyyy")

YourMilliDate.getDateFromMilli("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

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

To check all the feature and functions available for KBEXTENSIONS, please visit the [here](http://www.dropwizard.io/1.0.2/docs/).
