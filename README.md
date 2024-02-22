# Cross Browser testing with Docker and Jenkins

## The Problem Statement:

We want to achieve Cross browser Testing for our Test project using Docker and Selenium with Jenkins Pipeline.

## Overview about Project:

Github link : https://github.com/dhvanikam/DockerSampleProject

We are using Hybrid framework for this TestProject,

- BDD approach with Cucumber (Here we have “Feature files” written in Gherkin Language and code associated with the steps are in “Step Definitions” file)
- Selenium + Java + TestNG
- Maven as dependency management
- Test Run from “Test Runner File”
- Git + Github for version control
- Jenkins for CI

## **Let’s Start, First Lets see what is Cross-Browser testing :**

***Cross browser testing*** refers to testing a website in **multiple browsers like IE, Chrome, Firefox** to check its efficacy on each. ***Cross browser compatibility*** is the ability of the website or web application to function across **different browsers and operating systems**.

![crossbrowser](https://github.com/dhvanikam/DockerSampleProject/assets/73573915/a3cdf97d-0204-489d-9ef6-8e4d09b99126)


## Solution#1 :

Third Party Solution like [Sauce Labs](https://saucelabs.com/), [Browser Stack](https://www.browserstack.com/), But we did not wanted to use third party solution for this project.

## Solution#2 :

Next solution comes in to mind while talking about cross browser testing is **Selenium Grid.**

### **What is Selenium Grid?**

Selenium grid helps to master the computer (hub) to distributed test cases among the slave machines (nodes).

When we implement all the test cases in one machine at some point there might be some limitations, and sometimes one single machine will not be sufficient enough to run all the test cases and that point of time Selenium grid comes into the role.

![seleniumgrid](https://github.com/dhvanikam/DockerSampleProject/assets/73573915/299771bb-cefa-43e2-9b35-8eb42fe4be15)


**Fore more details  :** [https://www.selenium.dev/documentation/grid](https://www.selenium.dev/documentation/grid/)

## Solution#3 :

You must be thinking why we need docker when we have selenium grid?

Usually while configuring the Selenium grid we need to host **multiple virtual machines as nodes** and we need to connect every single node with the hub. Also, when we set up a normal grid we need to download the Selenium server jar file and run that jar file on each computer in which we are going to set up the Selenium grid. This is **costly** and sometimes a **time-consuming task** for the testers.

Docker that uses Selenium grid and it overcome some of the Selenium Grid Drawback and have additional advantages.

So Lets Dive in to the main topic here,

## What is Docker

Docker is an open-source platform that automates the deployment of application in lightweight portable containers. These containers package applications and their dependencies together, ensuring consistency across different environments.

## **How can we use Docker for Testing?**

![DockerSeleniumGrid](https://github.com/dhvanikam/DockerSampleProject/assets/73573915/59da1fb8-5a7d-435a-857e-074ae3f5031c)



When it comes to Selenium automation testing, it is important that a test run in one execution environment does not hinder the execution of tests run in another test environment (s). Hence, automation tests should be run in isolation, and Docker helps in realizing this ‘essential’ requirement.

- **Scalable and Reliable** : You can easily add or remove containers as needed. This can be helpful if you need to increase the number of tests that you are running or if you need to test on many browsers or devices.
- **Less overhead of installations** : Easy installation and maintenance.
- **Lesser chances of discrepancies** : Docker creates isolated environments for each test, which ensures that each test runs in a clean environment that is free of any dependencies or configurations from other tests. This can help prevent test failures caused by environmental factors.
- **Reduced test execution time** : Docker can help you run multiple tests in parallel, which can significantly reduce the time it takes to execute all your tests. This is especially beneficial for large test suites that can take a long time to execute.

Before we Go ahead with the setup, if this is your first time with Docker and Jenkins Pipeline, No worries !! 

**Docker Basics :** Visit link to get details for docker and how it works with Selenium.

https://github.com/dhvanikam/Docker

**Jenkins Pipeline Basics** : Visit link for details regarding How to create your first Pipeline using Jenkins

https://github.com/dhvanikam/JenkinsPipeline

# **Pre-requisites for Docker + Selenium Cross browser Setup**

First of all, we need to set up an environment to run the Selenium script.

- JDK 1.7 or a later version
- Chrome and Firefox browsers installed on your local machine
- Project with Selenium WebDriver and BDD-based test cases.
- Jenkinsfile with **pipeline script**
- **Docker Compose YML file** which helps define and share **multi-container applications**.

 Here's a step-by-step guide on how to set up cross-browser testing with Selenium, Docker, and Jenkins:

## **Set up Selenium Scripts to run on Selenium Grid using Docker:**

- [Install Docker](https://docs.docker.com/engine/install/) on the machine where Jenkins is running and make sure docker engine is running.
- Ensure that the Selenium tests are written and configured to run with Docker, as described in sample project.

As we are getting browser value from command line we are using “***System.getProperty("browser");*”** here in below code snippet.

Get the Browser value : **Hooks.java**

```java
@Before(order = 0)
	public static void before() throws Throwable {
		String browser = **System.getProperty("browser");**
		driverfactory = new DriverFactory();
		driver = driverfactory.initializeDrivers(browser);

	}
```

Initialize WebDriver : **DriverFactory.java**

Point **RemoteWebDriver** in your test to [http://localhost:4444](http://localhost:4444/)

Set Platform and Browser.

```java
if (browser.equalsIgnoreCase("firefox")) {
			Loggerload.info("Testing on firefox");
			WebDriverManager.firefoxdriver().setup();	
			DesiredCapabilities capability = new DesiredCapabilities();
			capability.setPlatform(Platform.LINUX);
			capability.setBrowserName(browser);
			driver = new RemoteWebDriver(new URL("http://localhost:4444/"), capability);

		} else if (browser.equalsIgnoreCase("chrome")) {
			Loggerload.info("Testing on chrome");
			DesiredCapabilities capability = new DesiredCapabilities();
			capability.setPlatform(Platform.LINUX);
			capability.setBrowserName(browser);
			WebDriverManager.chromedriver().setup();
			driver = new RemoteWebDriver(new URL("http://localhost:4444/"), capability);
}
```

## **Configure Jenkins** pipeline script**:**

- Create a Jenkins [pipeline](https://www.jenkins.io/doc/book/pipeline/jenkinsfile/) for testing project.
- Here we are going to follow few steps while creating pipeline script,
    - **Stage 1:** Verify required tools are installed
    - **Stage 2:** Clean running container
    - **Stage 3:** Start Container
    - **Stage 4:** Compile Stage
    - **Stage 5:** Testing Stage (Parallel)
        - Test with chrome
        - Test with firefox
    - **Post action** : Stop Container and Publish Allure Reports

Jenkinsfile of pipeline script in your project,

<img width="295" alt="Screenshot_2023-06-27_at_6 02 31_PM" src="https://github.com/dhvanikam/DockerSampleProject/assets/73573915/dd70107a-0339-486f-8eb2-e32e4a8bfb70">


Jenkinsfile should look like, 

```groovy
pipeline {
    agent any
    stages {
        stage('verify tooling') {
            steps {
                sh '''
          docker version
          docker info
          docker compose version
        '''
            }
        }

        stage('Clean running container') {
            steps {
                sh 'docker system prune -a --volumes -f'
            }
        }

        stage('Start container') {
            steps {
                retry(2) {
                    sh 'docker-compose -f docker-compose-v3.yml up -d --scale chrome=2 --scale firefox=2'
                    sh 'docker compose -f docker-compose-v3.yml ps'
                }
            }
        }

        stage('Compile Stage') {
            steps {
                withMaven(maven:'MyMaven') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Testing Stage') {
            parallel {
                stage('Test with chrome') {
                    steps {
                        withMaven(maven:'MyMaven') {
                            sh 'mvn test -Dbrowser=chrome -Dallure.results.directory=./target/chrome/allure-results'
                        }
                    }
                }

                stage('Test with firefox') {
                    steps {
                        withMaven(maven:'MyMaven') {
                            sh ' mvn test -Dbrowser=firefox -Dallure.results.directory=./target/firefox/allure-results'
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            allure([includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [
                            [path: 'target/chrome/allure-results'],
                            [path: 'target/firefox/allure-results']
                        ]
                    ])

            sh 'docker compose -f docker-compose-v3.yml down --remove-orphans -v'
        }
    }
}

```

- Make sure test are updated and Jenkinsfile is committed and pushed in to the repository.

## Jenkins setup :

- Configure the necessary plugins for running this project with Docker containers, such as the
    - **Docker**
    - **Pipeline**
    - **Git**
    - **GitHub**
    - **Blue Ocean**
    - **Maven Integration plugin**
- Create a new job and select the "**Pipeline**" type.

<img width="1497" alt="Step2" src="https://github.com/dhvanikam/DockerSampleProject/assets/73573915/7105106f-a5b9-474f-9064-945cf9082c60">


- Configure the job to **pull your Selenium test code** from a version control system (e.g., Git) or use a shared directory.

<img width="1890" alt="Screenshot 2024-02-22 at 2 36 43 PM" src="https://github.com/dhvanikam/DockerSampleProject/assets/73573915/fc26adba-7dc5-45b0-bd3f-ddfdc6dae7f6">


- Make sure  a Jenkins pipeline script is present in your SCM as mentioned above example.

<img width="477" alt="Step" src="https://github.com/dhvanikam/DockerSampleProject/assets/73573915/5e4fb695-b0e5-45f1-a522-5f08eba18dc0">


- Make sure “Script path” is same as in project,

 <img width="1866" alt="Screenshot 2024-02-22 at 2 46 34 PM" src="https://github.com/dhvanikam/DockerSampleProject/assets/73573915/52f0b6f7-2c85-4708-9984-b28cf253aa29">


- Save the Jenkins job configuration. Thats it!!
- Configure Jenkins job triggers and execution:
    - Set up triggers for your Jenkins job, such as scheduling the job to run at specific times or triggering it on code changes.
    - Define the execution environment for the Jenkins job to ensure it has Docker and other dependencies available.

## Run the Pipeline :

- Manually run the Jenkins job to verify that it executes your cross-browser tests using Selenium and Docker.
- Review the test results and logs to ensure everything is working correctly.



https://github.com/dhvanikam/DockerSampleProject/assets/73573915/dd12bca6-9b90-4bc0-95a0-925c8d9db048



Allure reports :

<img width="2212" alt="step22" src="https://github.com/dhvanikam/DockerSampleProject/assets/73573915/09986178-3c54-454f-b23e-f597a6874fa6">


Timeline will show parallel execution of the chrome and Firefox browsers

<img width="2212" alt="step21" src="https://github.com/dhvanikam/DockerSampleProject/assets/73573915/a6797b2d-4c64-4833-b6c1-0b7e9f61a0b2">


Reference :

**Jenkins Pipeline**: https://www.jenkins.io/doc/book/pipeline/

**Docker**: https://github.com/SeleniumHQ/docker-selenium, https://docs.docker.com/manuals/

**To create Flow Diagrams** : https://app.diagrams.net/
